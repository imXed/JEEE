package com.club.escalade.dao.api;

import com.club.escalade.dto.SortieRechercheCriteria;
import com.club.escalade.entity.Sortie;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface SortieDao {
    Page<Sortie> findAll(Pageable pageable);

    Optional<Sortie> findById(Long id);

    Optional<Sortie> findDetailById(Long id);

    Page<Sortie> search(SortieRechercheCriteria criteria, Pageable pageable);

    Sortie save(Sortie sortie);

    void deleteById(Long id);

    long count();
}

