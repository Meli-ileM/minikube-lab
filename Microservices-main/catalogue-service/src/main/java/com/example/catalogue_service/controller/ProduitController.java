package com.example.catalogue_service.controller;

import com.example.catalogue_service.dto.ProduitRequestDTO;
import com.example.catalogue_service.dto.ProduitResponseDTO;
import com.example.catalogue_service.service.ProduitService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/produits")
public class ProduitController {

    private static final Logger log = LoggerFactory.getLogger(ProduitController.class);

    private final ProduitService produitService;

    public ProduitController(ProduitService produitService) {
        this.produitService = produitService;
    }

    @GetMapping
    public ResponseEntity<List<ProduitResponseDTO>> getAllProduits() {
        log.debug("GET /api/v1/produits");
        return ResponseEntity.ok(produitService.getAllProduits());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProduitResponseDTO> getProduitById(@PathVariable Long id) {
        log.debug("GET /api/v1/produits/{}", id);
        return ResponseEntity.ok(produitService.getProduitById(id));
    }

    @PostMapping
    public ResponseEntity<ProduitResponseDTO> createProduit(@Valid @RequestBody ProduitRequestDTO dto) {
        log.debug("POST /api/v1/produits");
        ProduitResponseDTO created = produitService.createProduit(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProduitResponseDTO> updateProduit(@PathVariable Long id,
                                                             @Valid @RequestBody ProduitRequestDTO dto) {
        log.debug("PUT /api/v1/produits/{}", id);
        return ResponseEntity.ok(produitService.updateProduit(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduit(@PathVariable Long id) {
        log.debug("DELETE /api/v1/produits/{}", id);
        produitService.deleteProduit(id);
        return ResponseEntity.noContent().build();
    }
}
