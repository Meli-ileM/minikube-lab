package com.example.commande_service.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;

import java.util.List;

public class CommandeRequestDTO {

    @NotEmpty(message = "La commande doit contenir au moins une ligne")
    @Valid
    private List<LigneCommandeRequestDTO> lignes;

    public CommandeRequestDTO() {}

    public CommandeRequestDTO(List<LigneCommandeRequestDTO> lignes) {
        this.lignes = lignes;
    }

    public List<LigneCommandeRequestDTO> getLignes() { return lignes; }
    public void setLignes(List<LigneCommandeRequestDTO> lignes) { this.lignes = lignes; }
}
