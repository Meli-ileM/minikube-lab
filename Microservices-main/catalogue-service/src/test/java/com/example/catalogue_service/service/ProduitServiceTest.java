package com.example.catalogue_service.service;

import com.example.catalogue_service.domain.Produit;
import com.example.catalogue_service.dto.ProduitMapper;
import com.example.catalogue_service.dto.ProduitRequestDTO;
import com.example.catalogue_service.dto.ProduitResponseDTO;
import com.example.catalogue_service.exception.ResourceNotFoundException;
import com.example.catalogue_service.repository.ProduitRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProduitServiceTest {

    @Mock
    private ProduitRepository produitRepository;

    @Spy
    private ProduitMapper produitMapper = new ProduitMapper();

    @InjectMocks
    private ProduitService produitService;

    private Produit produit;
    private ProduitRequestDTO requestDTO;

    @BeforeEach
    void setUp() {
        produit = new Produit();
        produit.setId(1L);
        produit.setNom("Laptop");
        produit.setDescription("Un ordinateur portable");
        produit.setPrix(999.99);
        produit.setQuantiteStock(10);

        requestDTO = new ProduitRequestDTO();
        requestDTO.setNom("Laptop");
        requestDTO.setDescription("Un ordinateur portable");
        requestDTO.setPrix(999.99);
        requestDTO.setQuantiteStock(10);
    }

    @Test
    void getAllProduits_shouldReturnList() {
        when(produitRepository.findAll()).thenReturn(Arrays.asList(produit));
        List<ProduitResponseDTO> result = produitService.getAllProduits();
        assertEquals(1, result.size());
        assertEquals("Laptop", result.get(0).getNom());
        verify(produitRepository, times(1)).findAll();
    }

    @Test
    void getProduitById_shouldReturnProduit() {
        when(produitRepository.findById(1L)).thenReturn(Optional.of(produit));
        ProduitResponseDTO result = produitService.getProduitById(1L);
        assertNotNull(result);
        assertEquals("Laptop", result.getNom());
        assertEquals(999.99, result.getPrix());
    }

    @Test
    void getProduitById_shouldThrowWhenNotFound() {
        when(produitRepository.findById(99L)).thenReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class, () -> produitService.getProduitById(99L));
    }

    @Test
    void createProduit_shouldReturnCreated() {
        when(produitRepository.save(any(Produit.class))).thenReturn(produit);
        ProduitResponseDTO result = produitService.createProduit(requestDTO);
        assertNotNull(result);
        assertEquals("Laptop", result.getNom());
        verify(produitRepository, times(1)).save(any(Produit.class));
    }

    @Test
    void updateProduit_shouldReturnUpdated() {
        when(produitRepository.findById(1L)).thenReturn(Optional.of(produit));
        when(produitRepository.save(any(Produit.class))).thenReturn(produit);
        ProduitResponseDTO result = produitService.updateProduit(1L, requestDTO);
        assertNotNull(result);
        verify(produitRepository, times(1)).save(any(Produit.class));
    }

    @Test
    void deleteProduit_shouldDelete() {
        when(produitRepository.existsById(1L)).thenReturn(true);
        doNothing().when(produitRepository).deleteById(1L);
        produitService.deleteProduit(1L);
        verify(produitRepository, times(1)).deleteById(1L);
    }

    @Test
    void deleteProduit_shouldThrowWhenNotFound() {
        when(produitRepository.existsById(99L)).thenReturn(false);
        assertThrows(ResourceNotFoundException.class, () -> produitService.deleteProduit(99L));
    }
}
