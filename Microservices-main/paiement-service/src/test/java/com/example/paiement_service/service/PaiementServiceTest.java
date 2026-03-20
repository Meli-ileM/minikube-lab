package com.example.paiement_service.service;

import com.example.paiement_service.client.CommandeClient;
import com.example.paiement_service.domain.Paiement;
import com.example.paiement_service.dto.*;
import com.example.paiement_service.exception.ResourceNotFoundException;
import com.example.paiement_service.repository.PaiementRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PaiementServiceTest {

    @Mock
    private PaiementRepository paiementRepository;

    @Spy
    private PaiementMapper paiementMapper = new PaiementMapper();

    @Mock
    private CommandeClient commandeClient;

    @InjectMocks
    private PaiementService paiementService;

    private Paiement paiement;
    private CommandeDTO commandeDTO;

    @BeforeEach
    void setUp() {
        paiement = new Paiement();
        paiement.setId(1L);
        paiement.setCommandeId(1L);
        paiement.setMontant(1999.98);
        paiement.setModePaiement(Paiement.ModePaiement.CARTE_BANCAIRE);
        paiement.setStatut(Paiement.StatutPaiement.ACCEPTE);
        paiement.setDatePaiement(LocalDateTime.now());

        commandeDTO = new CommandeDTO();
        commandeDTO.setId(1L);
        commandeDTO.setDateCommande(LocalDateTime.now());
        commandeDTO.setStatut("EN_ATTENTE");
        commandeDTO.setMontantTotal(1999.98);
    }

    @Test
    void getAllPaiements_shouldReturnList() {
        when(paiementRepository.findAll()).thenReturn(Arrays.asList(paiement));
        List<PaiementResponseDTO> result = paiementService.getAllPaiements();
        assertEquals(1, result.size());
        assertEquals(1999.98, result.get(0).getMontant());
        verify(paiementRepository, times(1)).findAll();
    }

    @Test
    void getPaiementById_shouldReturnPaiement() {
        when(paiementRepository.findById(1L)).thenReturn(Optional.of(paiement));
        PaiementResponseDTO result = paiementService.getPaiementById(1L);
        assertNotNull(result);
        assertEquals("CARTE_BANCAIRE", result.getModePaiement());
    }

    @Test
    void getPaiementById_shouldThrowWhenNotFound() {
        when(paiementRepository.findById(99L)).thenReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class, () -> paiementService.getPaiementById(99L));
    }

    @Test
    void createPaiement_shouldCallCommandeAndSave() {
        when(commandeClient.getCommandeById(1L)).thenReturn(commandeDTO);
        when(paiementRepository.save(any(Paiement.class))).thenReturn(paiement);

        PaiementRequestDTO requestDTO = new PaiementRequestDTO();
        requestDTO.setCommandeId(1L);
        requestDTO.setModePaiement("CARTE_BANCAIRE");

        PaiementResponseDTO result = paiementService.createPaiement(requestDTO);
        assertNotNull(result);
        verify(commandeClient, times(1)).getCommandeById(1L);
        verify(paiementRepository, times(1)).save(any(Paiement.class));
    }

    @Test
    void deletePaiement_shouldDelete() {
        when(paiementRepository.existsById(1L)).thenReturn(true);
        doNothing().when(paiementRepository).deleteById(1L);
        paiementService.deletePaiement(1L);
        verify(paiementRepository, times(1)).deleteById(1L);
    }

    @Test
    void deletePaiement_shouldThrowWhenNotFound() {
        when(paiementRepository.existsById(99L)).thenReturn(false);
        assertThrows(ResourceNotFoundException.class, () -> paiementService.deletePaiement(99L));
    }
}
