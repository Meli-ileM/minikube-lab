package com.example.catalogue_service.service;

import com.example.catalogue_service.domain.Produit;
import com.example.catalogue_service.dto.ProduitMapper;
import com.example.catalogue_service.dto.ProduitRequestDTO;
import com.example.catalogue_service.dto.ProduitResponseDTO;
import com.example.catalogue_service.exception.ResourceNotFoundException;
import com.example.catalogue_service.repository.ProduitRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class ProduitService {

    private static final Logger log = LoggerFactory.getLogger(ProduitService.class);

    private final ProduitRepository produitRepository;
    private final ProduitMapper produitMapper;

    public ProduitService(ProduitRepository produitRepository, ProduitMapper produitMapper) {
        this.produitRepository = produitRepository;
        this.produitMapper = produitMapper;
    }

    public List<ProduitResponseDTO> getAllProduits() {
        log.info("Fetching all produits");
        return produitRepository.findAll().stream()
                .map(produitMapper::toDTO)
                .collect(Collectors.toList());
    }

    public ProduitResponseDTO getProduitById(Long id) {
        log.info("Fetching produit with id: {}", id);
        Produit produit = produitRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Produit non trouvé avec l'id: " + id));
        return produitMapper.toDTO(produit);
    }

    public ProduitResponseDTO createProduit(ProduitRequestDTO dto) {
        log.info("Creating produit: {}", dto.getNom());
        Produit produit = produitMapper.toEntity(dto);
        Produit saved = produitRepository.save(produit);
        return produitMapper.toDTO(saved);
    }

    public ProduitResponseDTO updateProduit(Long id, ProduitRequestDTO dto) {
        log.info("Updating produit with id: {}", id);
        Produit produit = produitRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Produit non trouvé avec l'id: " + id));
        produitMapper.updateEntity(produit, dto);
        Produit updated = produitRepository.save(produit);
        return produitMapper.toDTO(updated);
    }

    public void deleteProduit(Long id) {
        log.info("Deleting produit with id: {}", id);
        if (!produitRepository.existsById(id)) {
            throw new ResourceNotFoundException("Produit non trouvé avec l'id: " + id);
        }
        produitRepository.deleteById(id);
    }
}
