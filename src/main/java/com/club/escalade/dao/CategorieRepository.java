package com.club.escalade.dao;

import com.club.escalade.entity.Categorie;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

/**
 * Accès aux données des catégories.
 */
public interface CategorieRepository extends JpaRepository<Categorie, Long> {

    List<Categorie> findByNomContainingIgnoreCase(String nom);

    @EntityGraph(attributePaths = "sorties")
    Optional<Categorie> findWithSortiesById(Long id);
}
