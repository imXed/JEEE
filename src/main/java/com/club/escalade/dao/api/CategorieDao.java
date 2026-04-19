package com.club.escalade.dao.api;

import com.club.escalade.entity.Categorie;

import java.util.List;
import java.util.Optional;

public interface CategorieDao {
    List<Categorie> findAll();

    Optional<Categorie> findById(Long id);

    Optional<Categorie> findByIdWithSorties(Long id);

    List<Categorie> findByNomContaining(String nom);

    Categorie save(Categorie categorie);

    void deleteById(Long id);

    long count();
}

