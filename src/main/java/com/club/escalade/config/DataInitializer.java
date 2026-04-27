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

import java.text.Normalizer;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Random;
import java.util.Set;

@Component
public class DataInitializer implements CommandLineRunner {
    private static final int SORTIE_DATE_RANGE_DAYS = 365;
    private static final int SORTIE_SITE_WEB_INTERVAL = 3;
    private static final int MIN_CATEGORIES = 10;
    private static final int MAX_CATEGORIES = 20;
    private static final int MIN_MEMBERS = 300;
    private static final int MAX_MEMBERS = 500;
    private static final int MIN_SORTIES = 1000;
    private static final int MAX_SORTIES = 3000;

    private static final List<String> PRENOMS = List.of(
            "Jean", "Marie", "Paul", "Lucie", "Thomas", "Emma", "Nicolas", "Julie", "Antoine", "Camille",
            "Hugo", "Sarah", "Louis", "Clara", "Alexandre", "Manon", "Maxime", "Léa", "Pierre", "Chloé",
            "Arthur", "Inès", "Baptiste", "Zoé", "Raphaël", "Anaïs", "Jules", "Élise", "Noah", "Alice"
    );

    private static final List<String> NOMS = List.of(
            "Dupont", "Martin", "Bernard", "Thomas", "Robert", "Petit", "Durand", "Leroy", "Moreau", "Simon",
            "Laurent", "Lefebvre", "Michel", "Garcia", "David", "Bertrand", "Roux", "Vincent", "Fournier", "Morel",
            "Girard", "Andre", "Lefevre", "Mercier", "Blanc", "Guerin", "Boyer", "Renaud", "Faure", "Chevalier"
    );

    private static final List<String> CATEGORIES_CATALOGUE = List.of(
            "Escalade sportive",
            "Bloc",
            "Alpinisme neige",
            "Alpinisme roche",
            "Randonnée",
            "Terrain d’aventure",
            "Via ferrata",
            "Cascade de glace",
            "Escalade en salle",
            "Grande voie",
            "Ski de randonnée",
            "Trail alpin",
            "Slackline",
            "Dry tooling",
            "Escalade trad",
            "Montagne été",
            "Montagne hiver",
            "Orientation",
            "Bivouac",
            "Course d’arête"
    );

    private final CategorieService categorieService;
    private final MembreService membreService;
    private final SortieService sortieService;
    private final Random random = new Random();

    @Value("${app.seed.enabled:true}")
    private boolean seedEnabled;

    @Value("${app.seed.categories:15}")
    private int categoriesToGenerate;

    @Value("${app.seed.members:400}")
    private int membersToGenerate;

    @Value("${app.seed.sorties:2000}")
    private int sortiesToGenerate;

    @Value("${app.seed.default-password:password}")
    private String defaultPassword;

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

        // Contraintes pédagogiques du lot 2: maintenir un dataset de volume réaliste au démarrage.
        List<Categorie> categories = createCategories(Math.max(MIN_CATEGORIES, Math.min(MAX_CATEGORIES, categoriesToGenerate)));
        List<Membre> membres = createMembers(Math.max(MIN_MEMBERS, Math.min(MAX_MEMBERS, membersToGenerate)));
        createSorties(Math.max(MIN_SORTIES, Math.min(MAX_SORTIES, sortiesToGenerate)), categories, membres);
    }

    private List<Categorie> createCategories(int count) {
        List<String> noms = new ArrayList<>(CATEGORIES_CATALOGUE);
        if (count > noms.size()) {
            int missing = count - noms.size();
            for (int i = 0; i < missing; i++) {
                noms.add("Catégorie supplémentaire " + (i + 1));
            }
        }

        List<Categorie> result = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            Categorie categorie = new Categorie();
            categorie.setNom(noms.get(i));
            result.add(categorieService.save(categorie));
        }
        return result;
    }

    private List<Membre> createMembers(int count) {
        List<Membre> result = new ArrayList<>();
        Set<String> usedEmails = new HashSet<>();
        for (int i = 0; i < count; i++) {
            String prenom = PRENOMS.get(random.nextInt(PRENOMS.size()));
            String nom = NOMS.get(random.nextInt(NOMS.size()));
            String email = generateUniqueEmail(prenom, nom, i + 1, usedEmails);
            Set<String> roles = i == 0 ? Set.of("ROLE_ADMIN", "ROLE_USER") : Set.of("ROLE_USER");
            result.add(saveMember(nom, prenom, email, defaultPassword, roles));
        }
        return result;
    }

    private void createSorties(int count, List<Categorie> categories, List<Membre> membres) {
        List<String> descriptions = Arrays.asList(
                "Sortie orientée progression technique et sécurité en milieu naturel.",
                "Journée conviviale pour pratiquer et partager les fondamentaux de la discipline.",
                "Itinéraire varié adapté au niveau du groupe avec encadrement du club.",
                "Séance d'entraînement en extérieur avec objectifs de progression individuels."
        );

        for (int i = 0; i < count; i++) {
            Categorie categorie = categories.get(random.nextInt(categories.size()));
            Membre createur = membres.get(random.nextInt(membres.size()));
            createSortie(
                    "Sortie " + categorie.getNom() + " #" + (i + 1),
                    descriptions.get(i % descriptions.size()),
                    i % SORTIE_SITE_WEB_INTERVAL == 0 ? "https://club.fr/sorties/" + (i + 1) : null,
                    LocalDate.now().plusDays(random.nextInt(SORTIE_DATE_RANGE_DAYS) + 1L),
                    createur,
                    categorie
            );
        }
    }

    private Membre saveMember(String nom, String prenom, String email, String password, Set<String> authorities) {
        Membre membre = new Membre();
        membre.setNom(nom);
        membre.setPrenom(prenom);
        membre.setEmail(email);
        membre.setMotDePasse(password);
        membre.setAuthorities(new HashSet<>(authorities));
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

    private String toEmailPart(String value) {
        String normalized = Normalizer.normalize(value, Normalizer.Form.NFD)
                .replaceAll("\\p{M}", "");
        return normalized.toLowerCase(Locale.ROOT).replaceAll("[^a-z0-9]", "");
    }

    private String generateUniqueEmail(String prenom, String nom, int initialSuffix, Set<String> usedEmails) {
        int suffix = initialSuffix;
        while (true) {
            String candidate = toEmailPart(prenom) + "." + toEmailPart(nom) + suffix + "@club.fr";
            if (usedEmails.add(candidate)) {
                return candidate;
            }
            suffix++;
        }
    }
}
