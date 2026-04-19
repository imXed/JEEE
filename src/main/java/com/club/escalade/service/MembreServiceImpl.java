package com.club.escalade.service;

import com.club.escalade.dao.api.MembreDao;
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
    // Détecte un hash BCrypt déjà encodé pour éviter un double encodage.
    private static final String BCRYPT_PATTERN = "^\\$2[aby]\\$\\d{2}\\$.*";

    private final MembreDao membreDao;
    private final PasswordEncoder passwordEncoder;

    public MembreServiceImpl(MembreDao membreDao, PasswordEncoder passwordEncoder) {
        this.membreDao = membreDao;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Membre> findAll() {
        return membreDao.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Membre> findById(Long id) {
        return membreDao.findById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Membre> findByIdWithSorties(Long id) {
        return membreDao.findByIdWithSorties(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Membre> findByEmail(String email) {
        return membreDao.findByEmail(email);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Membre> findByNom(String nom) {
        return membreDao.findByNomContaining(SearchTextUtils.normalizeForContains(nom));
    }

    @Override
    public Membre save(Membre membre) {
        String motDePasse = membre.getMotDePasse();
        if (motDePasse != null && !motDePasse.matches(BCRYPT_PATTERN)) {
            membre.setMotDePasse(passwordEncoder.encode(membre.getMotDePasse()));
        }

        return membreDao.save(membre);
    }

    @Override
    public void deleteById(Long id) {
        membreDao.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public long count() {
        return membreDao.count();
    }
}
