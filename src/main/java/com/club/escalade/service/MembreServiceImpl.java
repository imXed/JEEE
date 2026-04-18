package com.club.escalade.service;

import com.club.escalade.dao.MembreRepository;
import com.club.escalade.entity.Membre;
import com.club.escalade.util.SearchTextUtils;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Implémentation du service des membres.
 */
@Service
@Transactional
public class MembreServiceImpl implements MembreService {

    private final MembreRepository membreRepository;
    private final PasswordEncoder passwordEncoder;

    public MembreServiceImpl(MembreRepository membreRepository, PasswordEncoder passwordEncoder) {
        this.membreRepository = membreRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Membre> findAll() {
        return membreRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Membre> findById(Long id) {
        return membreRepository.findById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Membre> findByEmail(String email) {
        return membreRepository.findByEmail(email);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Membre> findByNom(String nom) {
        return membreRepository.findByNomContainingIgnoreCase(SearchTextUtils.normalizeForContains(nom));
    }

    @Override
    public Membre save(Membre membre) {
        if (membre.getMotDePasse() != null && !membre.getMotDePasse().startsWith("$2")) {
            membre.setMotDePasse(passwordEncoder.encode(membre.getMotDePasse()));
        }
        return membreRepository.save(membre);
    }

    @Override
    public void deleteById(Long id) {
        membreRepository.deleteById(id);
    }
}
