package com.club.escalade.controller;

import com.club.escalade.entity.Categorie;
import com.club.escalade.service.CategorieService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Optional;

@Controller
public class CategorieController {

    private final CategorieService categorieService;

    public CategorieController(CategorieService categorieService) {
        this.categorieService = categorieService;
    }

    @GetMapping("/categories")
    public String listCategories(Model model) {
        model.addAttribute("categories", categorieService.findAll());
        return "categories";
    }

    @GetMapping("/categories/{id}")
    public String showCategoryDetails(@PathVariable Long id, Model model) {
        model.addAttribute("categories", categorieService.findAll());

        Optional<Categorie> categorieOpt = categorieService.findById(id);
        if (categorieOpt.isEmpty()) {
            model.addAttribute("errorMessage", "Catégorie introuvable.");
            return "categories";
        }

        model.addAttribute("selectedCategorie", categorieOpt.get());
        return "categories";
    }
}
