package com.club.escalade.dao;

import com.club.escalade.entity.Sortie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

/**
 * Accès aux données des sorties.
 */
public interface SortieRepository extends JpaRepository<Sortie, Long> {

    List<Sortie> findByNomContainingIgnoreCase(String nom);

    /**
     * Recherche multi-critères des sorties.
     */
    @Query("""
            SELECT s
            FROM Sortie s
            LEFT JOIN s.createur c
            LEFT JOIN s.categorie cat
            WHERE (:nom IS NULL OR LOWER(s.nom) LIKE LOWER(CONCAT('%', :nom, '%')))
              AND (:categorieId IS NULL OR cat.id = :categorieId)
              AND (:createurId IS NULL OR c.id = :createurId)
              AND (:dateDebut IS NULL OR s.dateSortie >= :dateDebut)
              AND (:dateFin IS NULL OR s.dateSortie <= :dateFin)
            ORDER BY s.dateSortie ASC
            """)
    List<Sortie> rechercher(
            @Param("nom") String nom,
            @Param("categorieId") Long categorieId,
            @Param("createurId") Long createurId,
            @Param("dateDebut") LocalDate dateDebut,
            @Param("dateFin") LocalDate dateFin
    );
}
