package com.example.commande_service.controller;

import com.example.commande_service.dto.CommandeRequestDTO;
import com.example.commande_service.dto.CommandeResponseDTO;
import com.example.commande_service.service.CommandeService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/commandes")
public class CommandeController {

    private static final Logger log = LoggerFactory.getLogger(CommandeController.class);

    private final CommandeService commandeService;

    public CommandeController(CommandeService commandeService) {
        this.commandeService = commandeService;
    }

    @GetMapping
    public ResponseEntity<List<CommandeResponseDTO>> getAllCommandes() {
        log.debug("GET /api/v1/commandes");
        return ResponseEntity.ok(commandeService.getAllCommandes());
    }

    @GetMapping("/{id}")
    public ResponseEntity<CommandeResponseDTO> getCommandeById(@PathVariable Long id) {
        log.debug("GET /api/v1/commandes/{}", id);
        return ResponseEntity.ok(commandeService.getCommandeById(id));
    }

    @PostMapping
    public ResponseEntity<CommandeResponseDTO> createCommande(@Valid @RequestBody CommandeRequestDTO dto) {
        log.debug("POST /api/v1/commandes");
        CommandeResponseDTO created = commandeService.createCommande(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @PatchMapping("/{id}/statut")
    public ResponseEntity<CommandeResponseDTO> updateStatut(@PathVariable Long id,
                                                             @RequestParam String statut) {
        log.debug("PATCH /api/v1/commandes/{}/statut?statut={}", id, statut);
        return ResponseEntity.ok(commandeService.updateStatut(id, statut));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCommande(@PathVariable Long id) {
        log.debug("DELETE /api/v1/commandes/{}", id);
        commandeService.deleteCommande(id);
        return ResponseEntity.noContent().build();
    }
}
