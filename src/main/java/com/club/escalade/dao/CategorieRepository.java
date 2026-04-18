package com.club.escalade.dao;

import com.club.escalade.entity.Categorie;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Accès aux données des catégories.
 */
public interface CategorieRepository extends JpaRepository<Categorie, Long> {

    List<Categorie> findByNomContainingIgnoreCase(String nom);
}
