package com.example.product_catalog.services;

import com.example.product_catalog.domain.dtos.ProductResponseDTO;
import com.example.product_catalog.domain.dtos.ProductUpdateDTO;
import com.example.product_catalog.domain.models.Product;
import com.example.product_catalog.exceptions.ProductNotFoundException;
import com.example.product_catalog.repositorys.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UpdateProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ProductDTOMapperService dtoMapperService;

    public ProductResponseDTO execute(ProductUpdateDTO productUpdateDTO, Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException(id));
        product.update(productUpdateDTO);
        return dtoMapperService.toDTO(productRepository.save(product));
    }
}
