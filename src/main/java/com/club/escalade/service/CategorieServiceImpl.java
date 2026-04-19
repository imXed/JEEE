package com.club.escalade.service;

import com.club.escalade.dao.api.CategorieDao;
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

    private final CategorieDao categorieDao;

    public CategorieServiceImpl(CategorieDao categorieDao) {
        this.categorieDao = categorieDao;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Categorie> findAll() {
        return categorieDao.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Categorie> findById(Long id) {
        return categorieDao.findById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Categorie> findByIdWithSorties(Long id) {
        return categorieDao.findByIdWithSorties(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Categorie> findByNom(String nom) {
        return categorieDao.findByNomContaining(SearchTextUtils.normalizeForContains(nom));
    }

    @Override
    public Categorie save(Categorie categorie) {
        return categorieDao.save(categorie);
    }

    @Override
    public void deleteById(Long id) {
        categorieDao.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public long count() {
        return categorieDao.count();
    }
}
