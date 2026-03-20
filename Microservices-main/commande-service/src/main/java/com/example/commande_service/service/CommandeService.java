package com.example.commande_service.service;

import com.example.commande_service.client.CatalogueClient;
import com.example.commande_service.domain.Commande;
import com.example.commande_service.domain.LigneCommande;
import com.example.commande_service.dto.*;
import com.example.commande_service.exception.ResourceNotFoundException;
import com.example.commande_service.repository.CommandeRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class CommandeService {

    private static final Logger log = LoggerFactory.getLogger(CommandeService.class);

    private final CommandeRepository commandeRepository;
    private final CommandeMapper commandeMapper;
    private final CatalogueClient catalogueClient;

    public CommandeService(CommandeRepository commandeRepository, CommandeMapper commandeMapper,
                           CatalogueClient catalogueClient) {
        this.commandeRepository = commandeRepository;
        this.commandeMapper = commandeMapper;
        this.catalogueClient = catalogueClient;
    }

    public List<CommandeResponseDTO> getAllCommandes() {
        log.info("Fetching all commandes");
        return commandeRepository.findAll().stream()
                .map(commandeMapper::toDTO)
                .collect(Collectors.toList());
    }

    public CommandeResponseDTO getCommandeById(Long id) {
        log.info("Fetching commande with id: {}", id);
        Commande commande = commandeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Commande non trouvée avec l'id: " + id));
        return commandeMapper.toDTO(commande);
    }

    public CommandeResponseDTO createCommande(CommandeRequestDTO dto) {
        log.info("Creating new commande with {} lignes", dto.getLignes().size());

        Commande commande = new Commande();
        commande.setDateCommande(LocalDateTime.now());
        commande.setStatut(Commande.StatutCommande.EN_ATTENTE);
        commande.setMontantTotal(0.0);

        double total = 0.0;
        for (LigneCommandeRequestDTO ligneDTO : dto.getLignes()) {
            ProduitDTO produit = catalogueClient.getProduitById(ligneDTO.getProduitId());
            log.debug("Produit fetched via Feign: {} - prix: {}", produit.getNom(), produit.getPrix());

            LigneCommande ligne = new LigneCommande();
            ligne.setProduitId(produit.getId());
            ligne.setProduitNom(produit.getNom());
            ligne.setQuantite(ligneDTO.getQuantite());
            ligne.setPrixUnitaire(produit.getPrix());
            ligne.setCommande(commande);
            commande.getLignes().add(ligne);
            total += produit.getPrix() * ligneDTO.getQuantite();
        }

        commande.setMontantTotal(total);
        Commande saved = commandeRepository.save(commande);
        return commandeMapper.toDTO(saved);
    }

    public CommandeResponseDTO updateStatut(Long id, String statut) {
        log.info("Updating commande {} statut to {}", id, statut);
        Commande commande = commandeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Commande non trouvée avec l'id: " + id));
        commande.setStatut(Commande.StatutCommande.valueOf(statut));
        Commande updated = commandeRepository.save(commande);
        return commandeMapper.toDTO(updated);
    }

    public void deleteCommande(Long id) {
        log.info("Deleting commande with id: {}", id);
        if (!commandeRepository.existsById(id)) {
            throw new ResourceNotFoundException("Commande non trouvée avec l'id: " + id);
        }
        commandeRepository.deleteById(id);
    }
}
