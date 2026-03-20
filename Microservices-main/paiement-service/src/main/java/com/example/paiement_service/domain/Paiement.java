package com.example.paiement_service.domain;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "paiements")
public class Paiement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long commandeId;

    @Column(nullable = false)
    private Double montant;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private ModePaiement modePaiement;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private StatutPaiement statut;

    @Column(nullable = false)
    private LocalDateTime datePaiement;

    public enum ModePaiement {
        CARTE_BANCAIRE, VIREMENT, PAYPAL, ESPECES
    }

    public enum StatutPaiement {
        EN_ATTENTE, ACCEPTE, REFUSE, REMBOURSE
    }

    public Paiement() {}

    public Paiement(Long id, Long commandeId, Double montant, ModePaiement modePaiement, StatutPaiement statut, LocalDateTime datePaiement) {
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
    public ModePaiement getModePaiement() { return modePaiement; }
    public void setModePaiement(ModePaiement modePaiement) { this.modePaiement = modePaiement; }
    public StatutPaiement getStatut() { return statut; }
    public void setStatut(StatutPaiement statut) { this.statut = statut; }
    public LocalDateTime getDatePaiement() { return datePaiement; }
    public void setDatePaiement(LocalDateTime datePaiement) { this.datePaiement = datePaiement; }
}
