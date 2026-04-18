package com.club.escalade.service;

import com.club.escalade.dao.CategorieRepository;
import com.club.escalade.entity.Categorie;
import com.club.escalade.util.SearchTextUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Implémentation du service des catégories.
 */
@Service
@Transactional
public class CategorieServiceImpl implements CategorieService {

    private final CategorieRepository categorieRepository;

    public CategorieServiceImpl(CategorieRepository categorieRepository) {
        this.categorieRepository = categorieRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Categorie> findAll() {
        return categorieRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Categorie> findById(Long id) {
        return categorieRepository.findById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Categorie> findByNom(String nom) {
        return categorieRepository.findByNomContainingIgnoreCase(SearchTextUtils.normalizeForContains(nom));
    }

    @Override
    public Categorie save(Categorie categorie) {
        return categorieRepository.save(categorie);
    }

    @Override
    public void deleteById(Long id) {
        categorieRepository.deleteById(id);
    }
}
