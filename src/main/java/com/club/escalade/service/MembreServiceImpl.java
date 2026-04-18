package com.club.escalade.service;

import com.club.escalade.dao.MembreRepository;
import com.club.escalade.entity.Membre;
import com.club.escalade.util.SearchTextUtils;
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

    public MembreServiceImpl(MembreRepository membreRepository) {
        this.membreRepository = membreRepository;
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
        return membreRepository.save(membre);
    }

    @Override
    public void deleteById(Long id) {
        membreRepository.deleteById(id);
    }
}
