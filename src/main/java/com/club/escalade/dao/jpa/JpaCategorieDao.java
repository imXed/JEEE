package com.club.escalade.dao.jpa;

import com.club.escalade.dao.CategorieRepository;
import com.club.escalade.dao.api.CategorieDao;
import com.club.escalade.entity.Categorie;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class JpaCategorieDao implements CategorieDao {
    private final CategorieRepository categorieRepository;

    public JpaCategorieDao(CategorieRepository categorieRepository) {
        this.categorieRepository = categorieRepository;
    }

    @Override
    public List<Categorie> findAll() {
        return categorieRepository.findAll();
    }

    @Override
    public Optional<Categorie> findById(Long id) {
        return categorieRepository.findById(id);
    }

    @Override
    public Optional<Categorie> findByIdWithSorties(Long id) {
        return categorieRepository.findWithSortiesById(id);
    }

    @Override
    public List<Categorie> findByNomContaining(String nom) {
        return categorieRepository.findByNomContainingIgnoreCase(nom);
    }

    @Override
    public Categorie save(Categorie categorie) {
        return categorieRepository.save(categorie);
    }

    @Override
    public void deleteById(Long id) {
        categorieRepository.deleteById(id);
    }

    @Override
    public long count() {
        return categorieRepository.count();
    }
}

