package com.example.paiement_service.controller;

import com.example.paiement_service.dto.PaiementRequestDTO;
import com.example.paiement_service.dto.PaiementResponseDTO;
import com.example.paiement_service.service.PaiementService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/paiements")
public class PaiementController {

    private static final Logger log = LoggerFactory.getLogger(PaiementController.class);

    private final PaiementService paiementService;

    public PaiementController(PaiementService paiementService) {
        this.paiementService = paiementService;
    }

    @GetMapping
    public ResponseEntity<List<PaiementResponseDTO>> getAllPaiements() {
        log.debug("GET /api/v1/paiements");
        return ResponseEntity.ok(paiementService.getAllPaiements());
    }

    @GetMapping("/{id}")
    public ResponseEntity<PaiementResponseDTO> getPaiementById(@PathVariable Long id) {
        log.debug("GET /api/v1/paiements/{}", id);
        return ResponseEntity.ok(paiementService.getPaiementById(id));
    }

    @GetMapping("/commande/{commandeId}")
    public ResponseEntity<List<PaiementResponseDTO>> getPaiementsByCommandeId(@PathVariable Long commandeId) {
        log.debug("GET /api/v1/paiements/commande/{}", commandeId);
        return ResponseEntity.ok(paiementService.getPaiementsByCommandeId(commandeId));
    }

    @PostMapping
    public ResponseEntity<PaiementResponseDTO> createPaiement(@Valid @RequestBody PaiementRequestDTO dto) {
        log.debug("POST /api/v1/paiements");
        PaiementResponseDTO created = paiementService.createPaiement(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePaiement(@PathVariable Long id) {
        log.debug("DELETE /api/v1/paiements/{}", id);
        paiementService.deletePaiement(id);
        return ResponseEntity.noContent().build();
    }
}
