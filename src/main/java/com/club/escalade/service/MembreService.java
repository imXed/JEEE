package com.club.escalade.service;

import com.club.escalade.entity.Membre;

import java.util.List;
import java.util.Optional;

/**
 * Services métier des membres.
 */
public interface MembreService {

    List<Membre> findAll();

    Optional<Membre> findById(Long id);

    Optional<Membre> findByEmail(String email);

    List<Membre> findByNom(String nom);

    Membre save(Membre membre);

    void deleteById(Long id);
}
