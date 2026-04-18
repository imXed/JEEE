package com.club.escalade.service;

import com.club.escalade.dao.SortieRepository;
import com.club.escalade.dto.SortieRechercheCriteria;
import com.club.escalade.entity.Sortie;
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

    public SortieServiceImpl(SortieRepository sortieRepository) {
        this.sortieRepository = sortieRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Sortie> findAll() {
        return sortieRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Sortie> findById(Long id) {
        return sortieRepository.findById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Sortie> findByNom(String nom) {
        return sortieRepository.findByNomContainingIgnoreCase(normalizeForContains(nom));
    }

    @Override
    @Transactional(readOnly = true)
    public List<Sortie> rechercher(SortieRechercheCriteria criteria) {
        SortieRechercheCriteria safeCriteria = criteria == null ? new SortieRechercheCriteria() : criteria;

        if (safeCriteria.getDateDebut() != null
                && safeCriteria.getDateFin() != null
                && safeCriteria.getDateDebut().isAfter(safeCriteria.getDateFin())) {
            throw new IllegalArgumentException("La date de début ne peut pas être après la date de fin.");
        }

        return sortieRepository.rechercher(
                normalize(safeCriteria.getNom()),
                safeCriteria.getCategorieId(),
                safeCriteria.getCreateurId(),
                safeCriteria.getDateDebut(),
                safeCriteria.getDateFin()
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

    private String normalize(String value) {
        if (value == null) {
            return null;
        }
        String trimmed = value.trim();
        return trimmed.isEmpty() ? null : trimmed;
    }

    private String normalizeForContains(String value) {
        String normalized = normalize(value);
        return normalized == null ? "" : normalized;
    }
}
