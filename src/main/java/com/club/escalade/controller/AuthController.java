package com.club.escalade.controller;

import com.club.escalade.entity.Membre;
import com.club.escalade.service.MembreService;
import com.club.escalade.service.PasswordResetService;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jakarta.validation.Valid;

@Controller
public class AuthController {

    private final MembreService membreService;
    private final PasswordResetService passwordResetService;

    public AuthController(MembreService membreService, PasswordResetService passwordResetService) {
        this.membreService = membreService;
        this.passwordResetService = passwordResetService;
    }

    @GetMapping("/forgot-password")
    public String forgotPasswordPage() {
        return "forgot-password";
    }

    @PostMapping("/forgot-password")
    public String requestPasswordReset(
            @RequestParam String email,
            RedirectAttributes redirectAttributes,
            ServletWebRequest request
    ) {
        String baseUrl = request.getRequest().getScheme()
                + "://"
                + request.getRequest().getServerName()
                + ":"
                + request.getRequest().getServerPort()
                + request.getRequest().getContextPath();
        passwordResetService.requestReset(email, baseUrl);
        redirectAttributes.addFlashAttribute(
                "successMessage",
                "Si un compte existe avec cet email, un message de réinitialisation a été envoyé."
        );
        return "redirect:/forgot-password";
    }

    @GetMapping("/reset-password")
    public String resetPasswordPage(@RequestParam String token, Model model, RedirectAttributes redirectAttributes) {
        if (!passwordResetService.isValidToken(token)) {
            redirectAttributes.addFlashAttribute("errorMessage", "Lien invalide ou expiré.");
            return "redirect:/forgot-password";
        }
        model.addAttribute("token", token);
        return "reset-password";
    }

    @PostMapping("/reset-password")
    public String resetPassword(
            @RequestParam String token,
            @RequestParam String motDePasse,
            RedirectAttributes redirectAttributes
    ) {
        if (motDePasse == null || motDePasse.length() < 6) {
            redirectAttributes.addFlashAttribute("errorMessage", "Le mot de passe doit contenir au moins 6 caractères.");
            return "redirect:/reset-password?token=" + token;
        }
        boolean success = passwordResetService.resetPassword(token, motDePasse);
        if (!success) {
            redirectAttributes.addFlashAttribute("errorMessage", "Lien invalide ou expiré.");
            return "redirect:/forgot-password";
        }
        redirectAttributes.addFlashAttribute("successMessage", "Mot de passe modifié. Vous pouvez vous connecter.");
        return "redirect:/login";
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
