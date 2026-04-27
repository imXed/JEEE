package com.club.escalade.controller;

import com.club.escalade.service.PasswordResetService;
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
import org.springframework.web.util.UriUtils;

import jakarta.validation.Valid;

@Controller
public class AuthController {

    private final PasswordResetService passwordResetService;

    public AuthController(PasswordResetService passwordResetService) {
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
        ResetPasswordForm form = new ResetPasswordForm();
        form.setToken(token);
        model.addAttribute("resetPasswordForm", form);
        return "reset-password";
    }

    @PostMapping("/reset-password")
    public String resetPassword(
            @Valid @ModelAttribute("resetPasswordForm") ResetPasswordForm resetPasswordForm,
            BindingResult bindingResult,
            RedirectAttributes redirectAttributes
    ) {
        String encodedToken = UriUtils.encode(resetPasswordForm.getToken(), java.nio.charset.StandardCharsets.UTF_8);
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("errorMessage", "Le mot de passe doit contenir au moins 6 caractères.");
            return "redirect:/reset-password?token=" + encodedToken;
        }
        boolean success = passwordResetService.resetPassword(resetPasswordForm.getToken(), resetPasswordForm.getMotDePasse());
        if (!success) {
            redirectAttributes.addFlashAttribute("errorMessage", "Lien invalide ou expiré.");
            return "redirect:/forgot-password";
        }
        redirectAttributes.addFlashAttribute("successMessage", "Mot de passe modifié. Vous pouvez vous connecter.");
        return "redirect:/login";
    }

    @GetMapping({"/", "/home"})
    public String homePage() {
        return "home";
    }

    @GetMapping("/login")
    public String loginPage() {
        return "login";
    }

    public static class ResetPasswordForm {
        @NotBlank
        private String token;

        @NotBlank
        @Size(min = 6, max = 255)
        private String motDePasse;

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }

        public String getMotDePasse() {
            return motDePasse;
        }

        public void setMotDePasse(String motDePasse) {
            this.motDePasse = motDePasse;
        }
    }
}
