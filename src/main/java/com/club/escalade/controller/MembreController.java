package com.club.escalade.controller;

import com.club.escalade.dto.SortieRechercheCriteria;
import com.club.escalade.entity.Membre;
import com.club.escalade.entity.Sortie;
import com.club.escalade.service.MembreService;
import com.club.escalade.service.SortieService;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/membres")
public class MembreController {

    private final MembreService membreService;
    private final SortieService sortieService;

    public MembreController(MembreService membreService, SortieService sortieService) {
        this.membreService = membreService;
        this.sortieService = sortieService;
    }

    @GetMapping
    public String listMembres(Model model) {
        addAuthAttributes(model);
        model.addAttribute("membres", membreService.findAll());
        return "membres";
    }

    @GetMapping("/{id}")
    public String detailMembre(@PathVariable Long id, Model model, RedirectAttributes redirectAttributes) {
        Optional<Membre> membreOpt = membreService.findById(id);
        if (membreOpt.isEmpty()) {
            redirectAttributes.addFlashAttribute("errorMessage", "Membre introuvable.");
            return "redirect:/membres";
        }

        SortieRechercheCriteria criteria = new SortieRechercheCriteria();
        criteria.setCreateurId(id);
        List<Sortie> sorties = sortieService.rechercher(criteria);

        addAuthAttributes(model);
        model.addAttribute("membre", membreOpt.get());
        model.addAttribute("sorties", sorties);
        return "membre-detail";
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
}
