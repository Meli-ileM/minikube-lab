package com.example.commande_service.client;

import com.example.commande_service.dto.ProduitDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "catalogue-service")
public interface CatalogueClient {

    @GetMapping("/api/v1/produits/{id}")
    ProduitDTO getProduitById(@PathVariable("id") Long id);
}
