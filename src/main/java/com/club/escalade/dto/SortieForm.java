package com.club.escalade.dto;

import org.springframework.format.annotation.DateTimeFormat;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.time.LocalDate;

public class SortieForm {

    @NotBlank
    @Size(max = 150)
    private String nom;

    @NotBlank
    @Size(max = 2000)
    private String description;

    @Size(max = 500)
    private String siteWeb;

    @NotNull
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate dateSortie;

    @NotNull
    private Long categorieId;

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getSiteWeb() {
        return siteWeb;
    }

    public void setSiteWeb(String siteWeb) {
        this.siteWeb = siteWeb;
    }

    public LocalDate getDateSortie() {
        return dateSortie;
    }

    public void setDateSortie(LocalDate dateSortie) {
        this.dateSortie = dateSortie;
    }

    public Long getCategorieId() {
        return categorieId;
    }

    public void setCategorieId(Long categorieId) {
        this.categorieId = categorieId;
    }
}
