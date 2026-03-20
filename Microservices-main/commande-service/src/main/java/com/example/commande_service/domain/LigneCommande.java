package com.example.commande_service.domain;

import jakarta.persistence.*;

@Entity
@Table(name = "lignes_commande")
public class LigneCommande {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long produitId;

    private String produitNom;

    @Column(nullable = false)
    private Integer quantite;

    @Column(nullable = false)
    private Double prixUnitaire;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "commande_id")
    private Commande commande;

    public LigneCommande() {}

    public LigneCommande(Long id, Long produitId, String produitNom, Integer quantite, Double prixUnitaire, Commande commande) {
        this.id = id;
        this.produitId = produitId;
        this.produitNom = produitNom;
        this.quantite = quantite;
        this.prixUnitaire = prixUnitaire;
        this.commande = commande;
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
    public Commande getCommande() { return commande; }
    public void setCommande(Commande commande) { this.commande = commande; }
}
