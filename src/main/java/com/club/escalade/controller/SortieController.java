package com.club.escalade.controller;

import com.club.escalade.dto.SortieForm;
import com.club.escalade.dto.SortieRechercheCriteria;
import com.club.escalade.entity.Categorie;
import com.club.escalade.entity.Membre;
import com.club.escalade.entity.Sortie;
import com.club.escalade.service.CategorieService;
import com.club.escalade.service.MembreService;
import com.club.escalade.service.SortieService;
import jakarta.validation.Valid;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/sorties")
public class SortieController {

    private final SortieService sortieService;
    private final CategorieService categorieService;
    private final MembreService membreService;

    public SortieController(SortieService sortieService, CategorieService categorieService, MembreService membreService) {
        this.sortieService = sortieService;
        this.categorieService = categorieService;
        this.membreService = membreService;
    }

    @GetMapping
    public String listSorties(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            Model model
    ) {
        List<Sortie> sorties = sortieService.findAll();
        addAuthAttributes(model);
        addPaginationAttributes(model, sorties, page, size);
        model.addAttribute("categories", categorieService.findAll());
        model.addAttribute("isSearch", false);
        return "sorties";
    }

    @GetMapping("/{id}")
    public String detailSortie(@PathVariable Long id, Model model, RedirectAttributes redirectAttributes) {
        Optional<Sortie> sortieOpt = sortieService.findById(id);
        if (sortieOpt.isEmpty()) {
            redirectAttributes.addFlashAttribute("errorMessage", "Sortie introuvable.");
            return "redirect:/sorties";
        }

        addAuthAttributes(model);
        model.addAttribute("sortie", sortieOpt.get());
        return "detail";
    }

    @GetMapping("/search")
    public String searchSorties(
            @RequestParam(required = false) String nom,
            @RequestParam(required = false) Long categorieId,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            Model model
    ) {
        SortieRechercheCriteria criteria = new SortieRechercheCriteria();
        criteria.setNom(nom);
        criteria.setCategorieId(categorieId);
        criteria.setDateDebut(date);
        criteria.setDateFin(date);

        List<Sortie> sorties = sortieService.rechercher(criteria);
        addAuthAttributes(model);
        addPaginationAttributes(model, sorties, page, size);
        model.addAttribute("categories", categorieService.findAll());
        model.addAttribute("isSearch", true);
        model.addAttribute("nom", nom);
        model.addAttribute("categorieId", categorieId);
        model.addAttribute("date", date);
        return "sorties";
    }

    @GetMapping("/create")
    public String showCreateForm(Model model) {
        if (getCurrentMembre().isEmpty()) {
            return "redirect:/login";
        }

        model.addAttribute("sortieForm", new SortieForm());
        model.addAttribute("categories", categorieService.findAll());
        return "create-sortie";
    }

    @PostMapping("/create")
    public String createSortie(
            @Valid @ModelAttribute("sortieForm") SortieForm sortieForm,
            BindingResult bindingResult,
            Model model,
            RedirectAttributes redirectAttributes
    ) {
        Optional<Membre> currentMembreOpt = getCurrentMembre();
        if (currentMembreOpt.isEmpty()) {
            return "redirect:/login";
        }

        if (bindingResult.hasErrors()) {
            model.addAttribute("categories", categorieService.findAll());
            return "create-sortie";
        }

        Optional<Categorie> categorieOpt = categorieService.findById(sortieForm.getCategorieId());
        if (categorieOpt.isEmpty()) {
            model.addAttribute("categories", categorieService.findAll());
            model.addAttribute("errorMessage", "Catégorie invalide.");
            return "create-sortie";
        }

        Sortie sortie = new Sortie();
        sortie.setNom(sortieForm.getNom());
        sortie.setDescription(sortieForm.getDescription());
        sortie.setSiteWeb(sortieForm.getSiteWeb());
        sortie.setDateSortie(sortieForm.getDateSortie());
        sortie.setCategorie(categorieOpt.get());
        sortie.setCreateur(currentMembreOpt.get());

        sortieService.save(sortie);
        redirectAttributes.addFlashAttribute("successMessage", "Sortie créée avec succès.");
        return "redirect:/sorties";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model, RedirectAttributes redirectAttributes) {
        Optional<Membre> currentMembreOpt = getCurrentMembre();
        if (currentMembreOpt.isEmpty()) {
            return "redirect:/login";
        }

        Optional<Sortie> sortieOpt = sortieService.findById(id);
        if (sortieOpt.isEmpty()) {
            redirectAttributes.addFlashAttribute("errorMessage", "Sortie introuvable.");
            return "redirect:/sorties";
        }

        Sortie sortie = sortieOpt.get();
        if (!isOwner(sortie, currentMembreOpt.get())) {
            redirectAttributes.addFlashAttribute("errorMessage", "Vous ne pouvez modifier que vos propres sorties.");
            return "redirect:/sorties/" + id;
        }

        SortieForm sortieForm = new SortieForm();
        sortieForm.setNom(sortie.getNom());
        sortieForm.setDescription(sortie.getDescription());
        sortieForm.setSiteWeb(sortie.getSiteWeb());
        sortieForm.setDateSortie(sortie.getDateSortie());
        sortieForm.setCategorieId(sortie.getCategorie().getId());

        model.addAttribute("sortieId", id);
        model.addAttribute("sortieForm", sortieForm);
        model.addAttribute("categories", categorieService.findAll());
        return "edit-sortie";
    }

    @PostMapping("/edit/{id}")
    public String editSortie(
            @PathVariable Long id,
            @Valid @ModelAttribute("sortieForm") SortieForm sortieForm,
            BindingResult bindingResult,
            Model model,
            RedirectAttributes redirectAttributes
    ) {
        Optional<Membre> currentMembreOpt = getCurrentMembre();
        if (currentMembreOpt.isEmpty()) {
            return "redirect:/login";
        }

        Optional<Sortie> sortieOpt = sortieService.findById(id);
        if (sortieOpt.isEmpty()) {
            redirectAttributes.addFlashAttribute("errorMessage", "Sortie introuvable.");
            return "redirect:/sorties";
        }

        Sortie sortie = sortieOpt.get();
        if (!isOwner(sortie, currentMembreOpt.get())) {
            redirectAttributes.addFlashAttribute("errorMessage", "Vous ne pouvez modifier que vos propres sorties.");
            return "redirect:/sorties/" + id;
        }

        if (bindingResult.hasErrors()) {
            model.addAttribute("sortieId", id);
            model.addAttribute("categories", categorieService.findAll());
            return "edit-sortie";
        }

        Optional<Categorie> categorieOpt = categorieService.findById(sortieForm.getCategorieId());
        if (categorieOpt.isEmpty()) {
            model.addAttribute("sortieId", id);
            model.addAttribute("categories", categorieService.findAll());
            model.addAttribute("errorMessage", "Catégorie invalide.");
            return "edit-sortie";
        }

        sortie.setNom(sortieForm.getNom());
        sortie.setDescription(sortieForm.getDescription());
        sortie.setSiteWeb(sortieForm.getSiteWeb());
        sortie.setDateSortie(sortieForm.getDateSortie());
        sortie.setCategorie(categorieOpt.get());

        sortieService.save(sortie);
        redirectAttributes.addFlashAttribute("successMessage", "Sortie modifiée avec succès.");
        return "redirect:/sorties/" + id;
    }

    @PostMapping("/delete/{id}")
    public String deleteSortie(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        Optional<Membre> currentMembreOpt = getCurrentMembre();
        if (currentMembreOpt.isEmpty()) {
            return "redirect:/login";
        }

        Optional<Sortie> sortieOpt = sortieService.findById(id);
        if (sortieOpt.isEmpty()) {
            redirectAttributes.addFlashAttribute("errorMessage", "Sortie introuvable.");
            return "redirect:/sorties";
        }

        Sortie sortie = sortieOpt.get();
        if (!isOwner(sortie, currentMembreOpt.get())) {
            redirectAttributes.addFlashAttribute("errorMessage", "Vous ne pouvez supprimer que vos propres sorties.");
            return "redirect:/sorties/" + id;
        }

        sortieService.deleteById(id);
        redirectAttributes.addFlashAttribute("successMessage", "Sortie supprimée avec succès.");
        return "redirect:/sorties";
    }

    private Optional<Membre> getCurrentMembre() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null
                || !authentication.isAuthenticated()
                || authentication instanceof AnonymousAuthenticationToken) {
            return Optional.empty();
        }
        return membreService.findByEmail(authentication.getName());
    }

    private void addAuthAttributes(Model model) {
        Optional<Membre> currentMembreOpt = getCurrentMembre();
        model.addAttribute("isAuthenticated", currentMembreOpt.isPresent());
        currentMembreOpt.map(Membre::getId).ifPresent(id -> model.addAttribute("currentMembreId", id));
    }

    private boolean isOwner(Sortie sortie, Membre membre) {
        return sortie.getCreateur() != null
                && sortie.getCreateur().getId() != null
                && sortie.getCreateur().getId().equals(membre.getId());
    }

    private void addPaginationAttributes(Model model, List<Sortie> allSorties, int page, int size) {
        int safeSize = size <= 0 ? 10 : size;
        int totalElements = allSorties == null ? 0 : allSorties.size();
        int totalPages = totalElements == 0 ? 1 : (int) Math.ceil((double) totalElements / safeSize);
        int safePage = Math.max(0, Math.min(page, totalPages - 1));
        int fromIndex = safePage * safeSize;
        int toIndex = Math.min(fromIndex + safeSize, totalElements);

        List<Sortie> pageContent;
        if (totalElements == 0) {
            pageContent = Collections.emptyList();
        } else {
            pageContent = allSorties.subList(fromIndex, toIndex);
        }

        model.addAttribute("sorties", pageContent);
        model.addAttribute("currentPage", safePage);
        model.addAttribute("pageSize", safeSize);
        model.addAttribute("totalPages", totalPages);
        model.addAttribute("totalElements", totalElements);
    }
}
