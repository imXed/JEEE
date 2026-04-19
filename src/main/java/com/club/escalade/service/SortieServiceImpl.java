package com.club.escalade.service;

import com.club.escalade.dao.SortieRepository;
import com.club.escalade.dao.api.SortieDao;
import com.club.escalade.dto.SortieRechercheCriteria;
import com.club.escalade.entity.Sortie;
import com.club.escalade.util.SearchTextUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Implémentation du service des sorties.
 */
@Service
@Transactional
public class SortieServiceImpl implements SortieService {

    private final SortieRepository sortieRepository;
    private final SortieDao sortieDao;

    public SortieServiceImpl(SortieRepository sortieRepository, SortieDao sortieDao) {
        this.sortieRepository = sortieRepository;
        this.sortieDao = sortieDao;
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Sortie> findAll(Pageable pageable) {
        return sortieDao.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Sortie> findById(Long id) {
        return sortieDao.findById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Sortie> findDetailById(Long id) {
        return sortieDao.findDetailById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Sortie> findByNom(String nom) {
        return sortieRepository.findByNomContainingIgnoreCase(SearchTextUtils.normalizeForContains(nom));
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Sortie> rechercher(SortieRechercheCriteria criteria, Pageable pageable) {
        SortieRechercheCriteria safeCriteria = criteria == null ? new SortieRechercheCriteria() : criteria;

        if (safeCriteria.getDateDebut() != null
                && safeCriteria.getDateFin() != null
                && safeCriteria.getDateDebut().isAfter(safeCriteria.getDateFin())) {
            throw new IllegalArgumentException("La date de début ne peut pas être après la date de fin.");
        }

        return sortieDao.search(safeCriteria, pageable);
    }

    @Override
    public Sortie save(Sortie sortie) {
        return sortieDao.save(sortie);
    }

    @Override
    public void deleteById(Long id) {
        sortieDao.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public long count() {
        return sortieDao.count();
    }

}
