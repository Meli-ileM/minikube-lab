package com.example.commande_service.service;

import com.example.commande_service.client.CatalogueClient;
import com.example.commande_service.domain.Commande;
import com.example.commande_service.domain.LigneCommande;
import com.example.commande_service.dto.*;
import com.example.commande_service.exception.ResourceNotFoundException;
import com.example.commande_service.repository.CommandeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CommandeServiceTest {

    @Mock
    private CommandeRepository commandeRepository;

    @Spy
    private CommandeMapper commandeMapper = new CommandeMapper();

    @Mock
    private CatalogueClient catalogueClient;

    @InjectMocks
    private CommandeService commandeService;

    private Commande commande;
    private ProduitDTO produitDTO;

    @BeforeEach
    void setUp() {
        LigneCommande ligne = new LigneCommande();
        ligne.setId(1L);
        ligne.setProduitId(1L);
        ligne.setProduitNom("Laptop");
        ligne.setQuantite(2);
        ligne.setPrixUnitaire(999.99);

        commande = new Commande();
        commande.setId(1L);
        commande.setDateCommande(LocalDateTime.now());
        commande.setStatut(Commande.StatutCommande.EN_ATTENTE);
        commande.setMontantTotal(1999.98);
        commande.setLignes(new ArrayList<>(List.of(ligne)));
        ligne.setCommande(commande);

        produitDTO = new ProduitDTO();
        produitDTO.setId(1L);
        produitDTO.setNom("Laptop");
        produitDTO.setPrix(999.99);
        produitDTO.setQuantiteStock(10);
    }

    @Test
    void getAllCommandes_shouldReturnList() {
        when(commandeRepository.findAll()).thenReturn(Arrays.asList(commande));
        List<CommandeResponseDTO> result = commandeService.getAllCommandes();
        assertEquals(1, result.size());
        assertEquals(1999.98, result.get(0).getMontantTotal());
        verify(commandeRepository, times(1)).findAll();
    }

    @Test
    void getCommandeById_shouldReturnCommande() {
        when(commandeRepository.findById(1L)).thenReturn(Optional.of(commande));
        CommandeResponseDTO result = commandeService.getCommandeById(1L);
        assertNotNull(result);
        assertEquals("EN_ATTENTE", result.getStatut());
    }

    @Test
    void getCommandeById_shouldThrowWhenNotFound() {
        when(commandeRepository.findById(99L)).thenReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class, () -> commandeService.getCommandeById(99L));
    }

    @Test
    void createCommande_shouldCallCatalogueAndSave() {
        when(catalogueClient.getProduitById(1L)).thenReturn(produitDTO);
        when(commandeRepository.save(any(Commande.class))).thenReturn(commande);

        LigneCommandeRequestDTO ligneReq = new LigneCommandeRequestDTO();
        ligneReq.setProduitId(1L);
        ligneReq.setQuantite(2);
        CommandeRequestDTO requestDTO = new CommandeRequestDTO();
        requestDTO.setLignes(List.of(ligneReq));

        CommandeResponseDTO result = commandeService.createCommande(requestDTO);
        assertNotNull(result);
        verify(catalogueClient, times(1)).getProduitById(1L);
        verify(commandeRepository, times(1)).save(any(Commande.class));
    }

    @Test
    void updateStatut_shouldUpdate() {
        when(commandeRepository.findById(1L)).thenReturn(Optional.of(commande));
        when(commandeRepository.save(any(Commande.class))).thenReturn(commande);
        CommandeResponseDTO result = commandeService.updateStatut(1L, "CONFIRMEE");
        assertNotNull(result);
        verify(commandeRepository, times(1)).save(any(Commande.class));
    }

    @Test
    void deleteCommande_shouldDelete() {
        when(commandeRepository.existsById(1L)).thenReturn(true);
        doNothing().when(commandeRepository).deleteById(1L);
        commandeService.deleteCommande(1L);
        verify(commandeRepository, times(1)).deleteById(1L);
    }

    @Test
    void deleteCommande_shouldThrowWhenNotFound() {
        when(commandeRepository.existsById(99L)).thenReturn(false);
        assertThrows(ResourceNotFoundException.class, () -> commandeService.deleteCommande(99L));
    }
}
