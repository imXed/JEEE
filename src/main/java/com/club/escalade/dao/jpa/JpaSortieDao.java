package com.club.escalade.dao.jpa;

import com.club.escalade.dao.SortieRepository;
import com.club.escalade.dao.api.SortieDao;
import com.club.escalade.dto.SortieRechercheCriteria;
import com.club.escalade.entity.Sortie;
import com.club.escalade.util.SearchTextUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class JpaSortieDao implements SortieDao {
    private final SortieRepository sortieRepository;

    public JpaSortieDao(SortieRepository sortieRepository) {
        this.sortieRepository = sortieRepository;
    }

    @Override
    public Page<Sortie> findAll(Pageable pageable) {
        return sortieRepository.findAll(pageable);
    }

    @Override
    public Optional<Sortie> findById(Long id) {
        return sortieRepository.findById(id);
    }

    @Override
    public Optional<Sortie> findDetailById(Long id) {
        return sortieRepository.findDetailById(id);
    }

    @Override
    public Page<Sortie> search(SortieRechercheCriteria criteria, Pageable pageable) {
        SortieRechercheCriteria safeCriteria = criteria == null ? new SortieRechercheCriteria() : criteria;
        return sortieRepository.rechercher(
                SearchTextUtils.normalize(safeCriteria.getNom()),
                safeCriteria.getCategorieId(),
                safeCriteria.getCreateurId(),
                safeCriteria.getDateDebut(),
                safeCriteria.getDateFin(),
                pageable
        );
    }

    @Override
    public Sortie save(Sortie sortie) {
        return sortieRepository.save(sortie);
    }

    @Override
    public void deleteById(Long id) {
        sortieRepository.deleteById(id);
    }

    @Override
    public long count() {
        return sortieRepository.count();
    }
}

