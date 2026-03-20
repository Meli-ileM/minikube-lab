package com.example.paiement_service.dto;

import jakarta.validation.constraints.NotNull;

public class PaiementRequestDTO {

    @NotNull(message = "L'id de la commande est obligatoire")
    private Long commandeId;

    @NotNull(message = "Le mode de paiement est obligatoire")
    private String modePaiement;

    public PaiementRequestDTO() {}

    public PaiementRequestDTO(Long commandeId, String modePaiement) {
        this.commandeId = commandeId;
        this.modePaiement = modePaiement;
    }

    public Long getCommandeId() { return commandeId; }
    public void setCommandeId(Long commandeId) { this.commandeId = commandeId; }
    public String getModePaiement() { return modePaiement; }
    public void setModePaiement(String modePaiement) { this.modePaiement = modePaiement; }
}
