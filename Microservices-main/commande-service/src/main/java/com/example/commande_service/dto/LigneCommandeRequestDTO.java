package com.example.commande_service.dto;

import jakarta.validation.constraints.*;

public class LigneCommandeRequestDTO {

    @NotNull(message = "L'id du produit est obligatoire")
    private Long produitId;

    @NotNull(message = "La quantité est obligatoire")
    @Min(value = 1, message = "La quantité doit être au moins 1")
    private Integer quantite;

    public LigneCommandeRequestDTO() {}

    public LigneCommandeRequestDTO(Long produitId, Integer quantite) {
        this.produitId = produitId;
        this.quantite = quantite;
    }

    public Long getProduitId() { return produitId; }
    public void setProduitId(Long produitId) { this.produitId = produitId; }
    public Integer getQuantite() { return quantite; }
    public void setQuantite(Integer quantite) { this.quantite = quantite; }
}
