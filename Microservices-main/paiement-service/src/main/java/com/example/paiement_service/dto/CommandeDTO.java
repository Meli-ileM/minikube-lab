package com.example.paiement_service.dto;

import java.time.LocalDateTime;

public class CommandeDTO {
    private Long id;
    private LocalDateTime dateCommande;
    private String statut;
    private Double montantTotal;

    public CommandeDTO() {}

    public CommandeDTO(Long id, LocalDateTime dateCommande, String statut, Double montantTotal) {
        this.id = id;
        this.dateCommande = dateCommande;
        this.statut = statut;
        this.montantTotal = montantTotal;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public LocalDateTime getDateCommande() { return dateCommande; }
    public void setDateCommande(LocalDateTime dateCommande) { this.dateCommande = dateCommande; }
    public String getStatut() { return statut; }
    public void setStatut(String statut) { this.statut = statut; }
    public Double getMontantTotal() { return montantTotal; }
    public void setMontantTotal(Double montantTotal) { this.montantTotal = montantTotal; }
}
