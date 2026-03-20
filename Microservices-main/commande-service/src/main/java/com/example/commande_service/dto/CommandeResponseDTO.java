package com.example.commande_service.dto;

import java.time.LocalDateTime;
import java.util.List;

public class CommandeResponseDTO {
    private Long id;
    private LocalDateTime dateCommande;
    private String statut;
    private Double montantTotal;
    private List<LigneCommandeResponseDTO> lignes;

    public CommandeResponseDTO() {}

    public CommandeResponseDTO(Long id, LocalDateTime dateCommande, String statut, Double montantTotal, List<LigneCommandeResponseDTO> lignes) {
        this.id = id;
        this.dateCommande = dateCommande;
        this.statut = statut;
        this.montantTotal = montantTotal;
        this.lignes = lignes;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public LocalDateTime getDateCommande() { return dateCommande; }
    public void setDateCommande(LocalDateTime dateCommande) { this.dateCommande = dateCommande; }
    public String getStatut() { return statut; }
    public void setStatut(String statut) { this.statut = statut; }
    public Double getMontantTotal() { return montantTotal; }
    public void setMontantTotal(Double montantTotal) { this.montantTotal = montantTotal; }
    public List<LigneCommandeResponseDTO> getLignes() { return lignes; }
    public void setLignes(List<LigneCommandeResponseDTO> lignes) { this.lignes = lignes; }
}
