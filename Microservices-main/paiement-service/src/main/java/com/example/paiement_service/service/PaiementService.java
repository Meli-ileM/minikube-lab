package com.example.paiement_service.service;

import com.example.paiement_service.client.CommandeClient;
import com.example.paiement_service.domain.Paiement;
import com.example.paiement_service.dto.*;
import com.example.paiement_service.exception.ResourceNotFoundException;
import com.example.paiement_service.repository.PaiementRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class PaiementService {

    private static final Logger log = LoggerFactory.getLogger(PaiementService.class);

    private final PaiementRepository paiementRepository;
    private final PaiementMapper paiementMapper;
    private final CommandeClient commandeClient;

    public PaiementService(PaiementRepository paiementRepository, PaiementMapper paiementMapper,
                           CommandeClient commandeClient) {
        this.paiementRepository = paiementRepository;
        this.paiementMapper = paiementMapper;
        this.commandeClient = commandeClient;
    }

    public List<PaiementResponseDTO> getAllPaiements() {
        log.info("Fetching all paiements");
        return paiementRepository.findAll().stream()
                .map(paiementMapper::toDTO)
                .collect(Collectors.toList());
    }

    public PaiementResponseDTO getPaiementById(Long id) {
        log.info("Fetching paiement with id: {}", id);
        Paiement paiement = paiementRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Paiement non trouvé avec l'id: " + id));
        return paiementMapper.toDTO(paiement);
    }

    public List<PaiementResponseDTO> getPaiementsByCommandeId(Long commandeId) {
        log.info("Fetching paiements for commande: {}", commandeId);
        return paiementRepository.findByCommandeId(commandeId).stream()
                .map(paiementMapper::toDTO)
                .collect(Collectors.toList());
    }

    public PaiementResponseDTO createPaiement(PaiementRequestDTO dto) {
        log.info("Creating paiement for commande: {}", dto.getCommandeId());

        CommandeDTO commande = commandeClient.getCommandeById(dto.getCommandeId());
        log.debug("Commande fetched via Feign: id={}, montant={}", commande.getId(), commande.getMontantTotal());

        Paiement paiement = new Paiement();
        paiement.setCommandeId(commande.getId());
        paiement.setMontant(commande.getMontantTotal());
        paiement.setModePaiement(Paiement.ModePaiement.valueOf(dto.getModePaiement()));
        paiement.setStatut(Paiement.StatutPaiement.ACCEPTE);
        paiement.setDatePaiement(LocalDateTime.now());

        Paiement saved = paiementRepository.save(paiement);
        return paiementMapper.toDTO(saved);
    }

    public void deletePaiement(Long id) {
        log.info("Deleting paiement with id: {}", id);
        if (!paiementRepository.existsById(id)) {
            throw new ResourceNotFoundException("Paiement non trouvé avec l'id: " + id);
        }
        paiementRepository.deleteById(id);
    }
}
