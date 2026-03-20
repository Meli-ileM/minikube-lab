package com.example.commande_service.dto;

public class LigneCommandeResponseDTO {
    private Long id;
    private Long produitId;
    private String produitNom;
    private Integer quantite;
    private Double prixUnitaire;

    public LigneCommandeResponseDTO() {}

    public LigneCommandeResponseDTO(Long id, Long produitId, String produitNom, Integer quantite, Double prixUnitaire) {
        this.id = id;
        this.produitId = produitId;
        this.produitNom = produitNom;
        this.quantite = quantite;
        this.prixUnitaire = prixUnitaire;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Long getProduitId() { return produitId; }
    public void setProduitId(Long produitId) { this.produitId = produitId; }
    public String getProduitNom() { return produitNom; }
    public void setProduitNom(String produitNom) { this.produitNom = produitNom; }
    public Integer getQuantite() { return quantite; }
    public void setQuantite(Integer quantite) { this.quantite = quantite; }
    public Double getPrixUnitaire() { return prixUnitaire; }
    public void setPrixUnitaire(Double prixUnitaire) { this.prixUnitaire = prixUnitaire; }
}
