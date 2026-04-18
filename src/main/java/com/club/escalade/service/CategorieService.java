package com.club.escalade.service;

import com.club.escalade.entity.Categorie;

import java.util.List;
import java.util.Optional;

/**
 * Services métier des catégories.
 */
public interface CategorieService {

    List<Categorie> findAll();

    Optional<Categorie> findById(Long id);

    List<Categorie> findByNom(String nom);

    Categorie save(Categorie categorie);

    void deleteById(Long id);
}
