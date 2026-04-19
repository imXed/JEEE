package com.club.escalade.dao.jpa;

import com.club.escalade.dao.MembreRepository;
import com.club.escalade.dao.api.MembreDao;
import com.club.escalade.entity.Membre;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class JpaMembreDao implements MembreDao {
    private final MembreRepository membreRepository;

    public JpaMembreDao(MembreRepository membreRepository) {
        this.membreRepository = membreRepository;
    }

    @Override
    public List<Membre> findAll() {
        return membreRepository.findAll();
    }

    @Override
    public Optional<Membre> findById(Long id) {
        return membreRepository.findById(id);
    }

    @Override
    public Optional<Membre> findByIdWithSorties(Long id) {
        return membreRepository.findWithSortiesCreeesById(id);
    }

    @Override
    public Optional<Membre> findByEmail(String email) {
        return membreRepository.findByEmail(email);
    }

    @Override
    public List<Membre> findByNomContaining(String nom) {
        return membreRepository.findByNomContainingIgnoreCase(nom);
    }

    @Override
    public Membre save(Membre membre) {
        return membreRepository.save(membre);
    }

    @Override
    public void deleteById(Long id) {
        membreRepository.deleteById(id);
    }

    @Override
    public long count() {
        return membreRepository.count();
    }
}

