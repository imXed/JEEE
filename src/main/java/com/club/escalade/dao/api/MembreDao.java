package com.club.escalade.dao.api;

import com.club.escalade.entity.Membre;

import java.util.List;
import java.util.Optional;

public interface MembreDao {
    List<Membre> findAll();

    Optional<Membre> findById(Long id);

    Optional<Membre> findByIdWithSorties(Long id);

    Optional<Membre> findByEmail(String email);

    List<Membre> findByNomContaining(String nom);

    Membre save(Membre membre);

    void deleteById(Long id);

    long count();
}

