package com.club.escalade.dao;

import com.club.escalade.entity.Membre;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

/**
 * Accès aux données des membres.
 */
public interface MembreRepository extends JpaRepository<Membre, Long> {

    Optional<Membre> findByEmail(String email);

    List<Membre> findByNomContainingIgnoreCase(String nom);

    @EntityGraph(attributePaths = "sortiesCreees")
    Optional<Membre> findWithSortiesCreeesById(Long id);
}
