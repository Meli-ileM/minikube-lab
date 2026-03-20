package com.example.paiement_service.dto;

import java.time.LocalDateTime;

public class PaiementResponseDTO {
    private Long id;
    private Long commandeId;
    private Double montant;
    private String modePaiement;
    private String statut;
    private LocalDateTime datePaiement;

    public PaiementResponseDTO() {}

    public PaiementResponseDTO(Long id, Long commandeId, Double montant, String modePaiement, String statut, LocalDateTime datePaiement) {
        this.id = id;
        this.commandeId = commandeId;
        this.montant = montant;
        this.modePaiement = modePaiement;
        this.statut = statut;
        this.datePaiement = datePaiement;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Long getCommandeId() { return commandeId; }
    public void setCommandeId(Long commandeId) { this.commandeId = commandeId; }
    public Double getMontant() { return montant; }
    public void setMontant(Double montant) { this.montant = montant; }
    public String getModePaiement() { return modePaiement; }
    public void setModePaiement(String modePaiement) { this.modePaiement = modePaiement; }
    public String getStatut() { return statut; }
    public void setStatut(String statut) { this.statut = statut; }
    public LocalDateTime getDatePaiement() { return datePaiement; }
    public void setDatePaiement(LocalDateTime datePaiement) { this.datePaiement = datePaiement; }
}
