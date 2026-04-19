package com.club.escalade.service;

import com.club.escalade.dao.PasswordResetTokenRepository;
import com.club.escalade.entity.Membre;
import com.club.escalade.entity.PasswordResetToken;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
@Transactional
public class PasswordResetServiceImpl implements PasswordResetService {
    private final MembreService membreService;
    private final PasswordResetTokenRepository tokenRepository;
    private final JavaMailSender mailSender;

    @Value("${app.password-reset.token-validity-minutes:60}")
    private long tokenValidityMinutes;

    @Value("${app.mail.from:no-reply@club-escalade.local}")
    private String mailFrom;

    public PasswordResetServiceImpl(
            MembreService membreService,
            PasswordResetTokenRepository tokenRepository,
            JavaMailSender mailSender
    ) {
        this.membreService = membreService;
        this.tokenRepository = tokenRepository;
        this.mailSender = mailSender;
    }

    @Override
    public void requestReset(String email, String appBaseUrl) {
        Optional<Membre> membreOpt = membreService.findByEmail(email);
        if (membreOpt.isEmpty()) {
            return;
        }

        Membre membre = membreOpt.get();
        tokenRepository.findByMembreIdAndUsedFalse(membre.getId()).forEach(token -> {
            token.setUsed(true);
            tokenRepository.save(token);
        });

        PasswordResetToken token = new PasswordResetToken();
        token.setToken(UUID.randomUUID().toString().replace("-", ""));
        token.setUsed(false);
        token.setMembre(membre);
        token.setExpiresAt(LocalDateTime.now().plusMinutes(tokenValidityMinutes));
        tokenRepository.save(token);

        String resetUrl = appBaseUrl + "/reset-password?token=" + token.getToken();
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(mailFrom);
        message.setTo(membre.getEmail());
        message.setSubject("Réinitialisation de votre mot de passe");
        message.setText("Bonjour " + membre.getPrenom() + ",\n\n"
                + "Pour réinitialiser votre mot de passe, cliquez sur ce lien :\n"
                + resetUrl + "\n\n"
                + "Ce lien est valable " + tokenValidityMinutes + " minutes.\n");
        mailSender.send(message);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean isValidToken(String token) {
        return tokenRepository.findByToken(token)
                .filter(t -> !t.isUsed())
                .filter(t -> t.getExpiresAt().isAfter(LocalDateTime.now()))
                .isPresent();
    }

    @Override
    public boolean resetPassword(String token, String newPassword) {
        Optional<PasswordResetToken> tokenOpt = tokenRepository.findByToken(token);
        if (tokenOpt.isEmpty()) {
            return false;
        }
        PasswordResetToken resetToken = tokenOpt.get();
        if (resetToken.isUsed() || resetToken.getExpiresAt().isBefore(LocalDateTime.now())) {
            return false;
        }

        Membre membre = resetToken.getMembre();
        membre.setMotDePasse(newPassword);
        membreService.save(membre);

        resetToken.setUsed(true);
        tokenRepository.save(resetToken);
        return true;
    }
}

