package com.club.escalade.controller;

import com.club.escalade.entity.Membre;
import com.club.escalade.service.MembreService;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jakarta.validation.Valid;

@Controller
public class AuthController {

    private final MembreService membreService;

    public AuthController(MembreService membreService) {
        this.membreService = membreService;
    }

    @GetMapping("/")
    public String homePage() {
        return "home";
    }

    @GetMapping("/login")
    public String loginPage() {
        return "login";
    }

    @GetMapping("/register")
    public String registerPage(Model model) {
        model.addAttribute("registerForm", new RegisterForm());
        return "register";
    }

    @PostMapping("/register")
    public String register(
            @Valid @ModelAttribute("registerForm") RegisterForm registerForm,
            BindingResult bindingResult,
            Model model,
            RedirectAttributes redirectAttributes
    ) {
        if (membreService.findByEmail(registerForm.getEmail()).isPresent()) {
            bindingResult.rejectValue("email", "email.exists", "Cet email est déjà utilisé.");
        }

        if (bindingResult.hasErrors()) {
            model.addAttribute("validationErrors", bindingResult.getAllErrors());
            return "register";
        }

        Membre membre = new Membre();
        membre.setNom(registerForm.getNom());
        membre.setPrenom(registerForm.getPrenom());
        membre.setEmail(registerForm.getEmail());
        membre.setMotDePasse(registerForm.getMotDePasse());

        membreService.save(membre);
        redirectAttributes.addFlashAttribute("successMessage", "Compte créé avec succès. Vous pouvez vous connecter.");
        return "redirect:/login";
    }

    public static class RegisterForm {
        @NotBlank
        @Size(max = 100)
        private String nom;

        @NotBlank
        @Size(max = 100)
        private String prenom;

        @NotBlank
        @Email
        @Size(max = 255)
        private String email;

        @NotBlank
        @Size(min = 6, max = 255)
        private String motDePasse;

        public String getNom() {
            return nom;
        }

        public void setNom(String nom) {
            this.nom = nom;
        }

        public String getPrenom() {
            return prenom;
        }

        public void setPrenom(String prenom) {
            this.prenom = prenom;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getMotDePasse() {
            return motDePasse;
        }

        public void setMotDePasse(String motDePasse) {
            this.motDePasse = motDePasse;
        }
    }
}
