package com.club.escalade.config;

import com.club.escalade.entity.Categorie;
import com.club.escalade.entity.Membre;
import com.club.escalade.entity.Sortie;
import com.club.escalade.service.CategorieService;
import com.club.escalade.service.MembreService;
import com.club.escalade.service.SortieService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Component
public class DataInitializer implements CommandLineRunner {
    // Répartition des dates des sorties générées sur une année glissante.
    private static final int SORTIE_DATE_RANGE_DAYS = 365;

    private final CategorieService categorieService;
    private final MembreService membreService;
    private final SortieService sortieService;

    @Value("${app.seed.enabled:true}")
    private boolean seedEnabled;

    @Value("${app.seed.large-dataset:true}")
    private boolean largeDatasetEnabled;

    @Value("${app.seed.large.categories:40}")
    private int largeCategories;

    @Value("${app.seed.large.members:300}")
    private int largeMembers;

    @Value("${app.seed.large.sorties:3000}")
    private int largeSorties;

    public DataInitializer(CategorieService categorieService, MembreService membreService, SortieService sortieService) {
        this.categorieService = categorieService;
        this.membreService = membreService;
        this.sortieService = sortieService;
    }

    @Override
    @Transactional
    public void run(String... args) {
        if (!seedEnabled || membreService.count() > 0 || categorieService.count() > 0 || sortieService.count() > 0) {
            return;
        }

        List<Categorie> categories = createBaseCategories();
        List<Membre> membres = createBaseMembers();
        createBaseSorties(categories, membres);

        if (largeDatasetEnabled) {
            createLargeDataset(categories, membres);
        }
    }

    private List<Categorie> createBaseCategories() {
        List<String> noms = List.of(
                "Alpinisme de roche",
                "Alpinisme de neige",
                "Alpinisme mixte",
                "Escalade sportive",
                "Randonnée du vertige",
                "Terrain d’aventure"
        );
        List<Categorie> result = new ArrayList<>();
        for (String nom : noms) {
            Categorie categorie = new Categorie();
            categorie.setNom(nom);
            result.add(categorieService.save(categorie));
        }
        return result;
    }

    private List<Membre> createBaseMembers() {
        List<Membre> result = new ArrayList<>();
        result.add(saveMember("Dupont", "Claire", "claire.dupont@club-escalade.fr", "password123"));
        result.add(saveMember("Martin", "Julien", "julien.martin@club-escalade.fr", "password123"));
        result.add(saveMember("Lefevre", "Sophie", "sophie.lefevre@club-escalade.fr", "password123"));
        return result;
    }

    private void createBaseSorties(List<Categorie> categories, List<Membre> membres) {
        createSortie("Falaise de Buoux", "Session grande voie sur les secteurs classiques de Buoux.",
                "https://www.buoux-escalade.fr", LocalDate.now().plusDays(30), membres.get(0), categories.get(3));
        createSortie("Traversée du Vercors", "Randonnée sur deux jours avec nuit en refuge.",
                null, LocalDate.now().plusDays(45), membres.get(1), categories.get(4));
        createSortie("Initiation Mont Blanc", "Sortie encadrée pour apprendre les bases de l'alpinisme.",
                "https://www.chamonix-guides.com", LocalDate.now().plusDays(60), membres.get(2), categories.get(1));
    }

    private void createLargeDataset(List<Categorie> categories, List<Membre> membres) {
        for (int i = 0; i < largeCategories; i++) {
            Categorie categorie = new Categorie();
            categorie.setNom("Catégorie supplémentaire " + (i + 1));
            categories.add(categorieService.save(categorie));
        }

        for (int i = 0; i < largeMembers; i++) {
            membres.add(saveMember(
                    "Nom" + (i + 1),
                    "Prenom" + (i + 1),
                    "membre" + (i + 1) + "@club-escalade.fr",
                    "password123"
            ));
        }

        for (int i = 0; i < largeSorties; i++) {
            Categorie categorie = categories.get(i % categories.size());
            Membre createur = membres.get(i % membres.size());
            createSortie(
                    "Sortie " + (i + 1),
                    "Description de la sortie " + (i + 1),
                    i % 3 == 0 ? "https://example.org/sortie/" + (i + 1) : null,
                    LocalDate.now().plusDays((i % SORTIE_DATE_RANGE_DAYS) + 1L),
                    createur,
                    categorie
            );
        }
    }

    private Membre saveMember(String nom, String prenom, String email, String password) {
        Membre membre = new Membre();
        membre.setNom(nom);
        membre.setPrenom(prenom);
        membre.setEmail(email);
        membre.setMotDePasse(password);
        return membreService.save(membre);
    }

    private void createSortie(String nom, String description, String siteWeb, LocalDate date, Membre createur, Categorie categorie) {
        Sortie sortie = new Sortie();
        sortie.setNom(nom);
        sortie.setDescription(description);
        sortie.setSiteWeb(siteWeb);
        sortie.setDateSortie(date);
        sortie.setCreateur(createur);
        sortie.setCategorie(categorie);
        sortieService.save(sortie);
    }
}
