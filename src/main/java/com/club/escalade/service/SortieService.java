package com.club.escalade.service;

import com.club.escalade.dto.SortieRechercheCriteria;
import com.club.escalade.entity.Sortie;

import java.util.List;
import java.util.Optional;

/**
 * Services métier des sorties.
 */
public interface SortieService {

    List<Sortie> findAll();

    Optional<Sortie> findById(Long id);

    List<Sortie> findByNom(String nom);

    List<Sortie> rechercher(SortieRechercheCriteria criteria);

    Sortie save(Sortie sortie);

    void deleteById(Long id);
}
