package com.club.escalade.dao;

import com.club.escalade.dao.api.CategorieDao;
import com.club.escalade.dao.api.MembreDao;
import com.club.escalade.dao.api.SortieDao;
import com.club.escalade.dto.SortieRechercheCriteria;
import com.club.escalade.entity.Categorie;
import com.club.escalade.entity.Membre;
import com.club.escalade.entity.Sortie;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@TestPropertySource(properties = {
        "app.seed.enabled=false",
        "spring.sql.init.mode=never"
})
class DaoIntegrationTest {

    @Autowired
    private CategorieDao categorieDao;

    @Autowired
    private MembreDao membreDao;

    @Autowired
    private SortieDao sortieDao;

    private Categorie categorie;
    private Membre membre;
    private Sortie sortie;

    @BeforeEach
    void setUp() {
        categorie = new Categorie();
        categorie.setNom("Escalade sportive");
        categorie = categorieDao.save(categorie);

        membre = new Membre();
        membre.setNom("Dupont");
        membre.setPrenom("Claire");
        membre.setEmail("claire@test.fr");
        membre.setMotDePasse("pwd");
        membre = membreDao.save(membre);

        sortie = new Sortie();
        sortie.setNom("Buoux");
        sortie.setDescription("Grande voie");
        sortie.setDateSortie(LocalDate.now().plusDays(5));
        sortie.setCreateur(membre);
        sortie.setCategorie(categorie);
        sortie = sortieDao.save(sortie);
    }

    @Test
    void categorieDao_shouldLoadWithSorties() {
        assertTrue(categorieDao.findAll().size() >= 1);
        assertTrue(categorieDao.findById(categorie.getId()).isPresent());
        assertTrue(categorieDao.findByIdWithSorties(categorie.getId()).isPresent());
    }

    @Test
    void membreDao_shouldLoadWithSortiesAndEmail() {
        assertTrue(membreDao.findByEmail("claire@test.fr").isPresent());
        assertTrue(membreDao.findByIdWithSorties(membre.getId()).isPresent());
        assertFalse(membreDao.findByNomContaining("Dup").isEmpty());
    }

    @Test
    void sortieDao_shouldSearchWithCriteriaAndPagination() {
        SortieRechercheCriteria criteria = new SortieRechercheCriteria();
        criteria.setNom("Buo");
        criteria.setCategorieId(categorie.getId());
        criteria.setCreateurId(membre.getId());
        criteria.setDateDebut(LocalDate.now());
        criteria.setDateFin(LocalDate.now().plusDays(30));

        Page<Sortie> page = sortieDao.search(criteria, PageRequest.of(0, 10));
        assertFalse(page.isEmpty());
        assertTrue(sortieDao.findDetailById(sortie.getId()).isPresent());
    }

    @Test
    void sortieDao_shouldDeleteSortie() {
        sortieDao.deleteById(sortie.getId());
        assertTrue(sortieDao.findById(sortie.getId()).isEmpty());
    }
}
