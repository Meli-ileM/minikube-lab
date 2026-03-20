package com.example.paiement_service.repository;

import com.example.paiement_service.domain.Paiement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PaiementRepository extends JpaRepository<Paiement, Long> {
    List<Paiement> findByCommandeId(Long commandeId);
}
