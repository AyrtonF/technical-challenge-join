package com.example.product_catalog.services;

import com.example.product_catalog.domain.dtos.ProductRequestDTO;
import com.example.product_catalog.domain.dtos.ProductResponseDTO;
import com.example.product_catalog.domain.models.Product;
import com.example.product_catalog.repositorys.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CreateProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ProductDTOMapperService dtoMapperService;

    public ProductResponseDTO execute(ProductRequestDTO productRequestDTO) {
        Product product = dtoMapperService.toEntity(productRequestDTO);
        return dtoMapperService.toDTO(productRepository.save(product));
    }
}
