package com.example.commande_service.dto;

import com.example.commande_service.domain.Commande;
import com.example.commande_service.domain.LigneCommande;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class CommandeMapper {

    public CommandeResponseDTO toDTO(Commande commande) {
        CommandeResponseDTO dto = new CommandeResponseDTO();
        dto.setId(commande.getId());
        dto.setDateCommande(commande.getDateCommande());
        dto.setStatut(commande.getStatut().name());
        dto.setMontantTotal(commande.getMontantTotal());
        dto.setLignes(commande.getLignes().stream()
                .map(this::toLigneDTO)
                .collect(Collectors.toList()));
        return dto;
    }

    public LigneCommandeResponseDTO toLigneDTO(LigneCommande ligne) {
        LigneCommandeResponseDTO dto = new LigneCommandeResponseDTO();
        dto.setId(ligne.getId());
        dto.setProduitId(ligne.getProduitId());
        dto.setProduitNom(ligne.getProduitNom());
        dto.setQuantite(ligne.getQuantite());
        dto.setPrixUnitaire(ligne.getPrixUnitaire());
        return dto;
    }
}
