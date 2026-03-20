package com.example.catalogue_service.dto;

import com.example.catalogue_service.domain.Produit;
import org.springframework.stereotype.Component;

@Component
public class ProduitMapper {

    public ProduitResponseDTO toDTO(Produit produit) {
        ProduitResponseDTO dto = new ProduitResponseDTO();
        dto.setId(produit.getId());
        dto.setNom(produit.getNom());
        dto.setDescription(produit.getDescription());
        dto.setPrix(produit.getPrix());
        dto.setQuantiteStock(produit.getQuantiteStock());
        return dto;
    }

    public Produit toEntity(ProduitRequestDTO dto) {
        Produit produit = new Produit();
        produit.setNom(dto.getNom());
        produit.setDescription(dto.getDescription());
        produit.setPrix(dto.getPrix());
        produit.setQuantiteStock(dto.getQuantiteStock());
        return produit;
    }

    public void updateEntity(Produit produit, ProduitRequestDTO dto) {
        produit.setNom(dto.getNom());
        produit.setDescription(dto.getDescription());
        produit.setPrix(dto.getPrix());
        produit.setQuantiteStock(dto.getQuantiteStock());
    }
}
