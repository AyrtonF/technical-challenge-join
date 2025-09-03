package com.example.product_catalog.services;

import com.example.product_catalog.domain.dtos.ProductResponseDTO;
import com.example.product_catalog.domain.models.Product;
import com.example.product_catalog.repositorys.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class GetAllProductsService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ProductDTOMapperService dtoMapperService;

    public Page<ProductResponseDTO> execute(String search, Pageable pageable) {
        Page<Product> productsPage;

        if (search == null || search.isBlank()) {
            productsPage = productRepository.findAll(pageable);
        } else {
            productsPage = productRepository.findByNameContainingIgnoreCase(search, pageable);
        }

        return productsPage.map(dtoMapperService::toDTO);
    }
}
