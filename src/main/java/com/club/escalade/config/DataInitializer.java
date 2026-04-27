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

import java.text.Normalizer;
import java.time.LocalDate;
import java.util.*;

@Component
public class DataInitializer implements CommandLineRunner {

    private final CategorieService categorieService;
    private final MembreService membreService;
    private final SortieService sortieService;

    private final Random random = new Random();

    @Value("${app.seed.enabled:true}")
    private boolean seedEnabled;

    @Value("${app.seed.default-password:password}")
    private String defaultPassword;

    private static final List<String> PRENOMS = List.of("Jean", "Marie", "Paul", "Lucie", "Thomas", "Emma");
    private static final List<String> NOMS = List.of("Dupont", "Martin", "Bernard", "Durand", "Leroy");

    private static final List<String> CATEGORIES = List.of(
            "Escalade sportive", "Bloc", "Alpinisme", "Randonnée", "Via ferrata"
    );

    public DataInitializer(CategorieService categorieService,
                           MembreService membreService,
                           SortieService sortieService) {
        this.categorieService = categorieService;
        this.membreService = membreService;
        this.sortieService = sortieService;
    }

    @Override
    public void run(String... args) {


        if (!seedEnabled) {
            return;
        }

        List<Categorie> categories = createCategories(10);
        List<Membre> membres = createMembers(50);
        createSorties(1000, categories, membres);

        System.out.println("DataInitializer END");
    }

    private List<Categorie> createCategories(int count) {
        List<Categorie> list = new ArrayList<>();

        for (int i = 0; i < count; i++) {
            Categorie c = new Categorie();
            c.setNom(CATEGORIES.get(i % CATEGORIES.size()));
            list.add(categorieService.save(c));
        }

        return list;
    }

    private List<Membre> createMembers(int count) {
        List<Membre> list = new ArrayList<>();
        Set<String> emails = new HashSet<>();

        for (int i = 0; i < count; i++) {
            String prenom = PRENOMS.get(random.nextInt(PRENOMS.size()));
            String nom = NOMS.get(random.nextInt(NOMS.size()));
            String email = generateEmail(prenom, nom, emails);

            Membre m = new Membre();
            m.setNom(nom);
            m.setPrenom(prenom);
            m.setEmail(email);
            m.setMotDePasse(defaultPassword);
            m.setAuthorities(Set.of("ROLE_USER"));

            list.add(membreService.save(m));
        }

        return list;
    }

    private void createSorties(int count, List<Categorie> categories, List<Membre> membres) {

        List<String> descriptions = List.of(
                "Sortie technique",
                "Sortie club conviviale",
                "Entraînement montagne",
                "Progression encadrée"
        );

        for (int i = 0; i < count; i++) {

            Sortie s = new Sortie();
            s.setNom("Sortie " + i);
            s.setDescription(descriptions.get(i % descriptions.size()));
            s.setSiteWeb(i % 3 == 0 ? "https://club.fr/" + i : null);
            s.setDateSortie(LocalDate.now().plusDays(random.nextInt(365)));

            s.setCategorie(categories.get(random.nextInt(categories.size())));
            s.setCreateur(membres.get(random.nextInt(membres.size())));

            sortieService.save(s);
        }
    }

    private String generateEmail(String prenom, String nom, Set<String> used) {
        String base = normalize(prenom) + "." + normalize(nom);
        String email;
        int i = 1;

        do {
            email = base + i + "@club.fr";
            i++;
        } while (!used.add(email));

        return email;
    }

    private String normalize(String s) {
        return Normalizer.normalize(s, Normalizer.Form.NFD)
                .replaceAll("\\p{M}", "")
                .toLowerCase();
    }
}