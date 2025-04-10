package com.example.product_catalog.services;


import com.example.product_catalog.domain.dtos.ProductRequestDTO;
import com.example.product_catalog.domain.dtos.ProductResponseDTO;
import com.example.product_catalog.domain.models.Product;
import com.example.product_catalog.repositorys.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductDTOMapperService {
    @Autowired
    private ProductRepository productRepository;


    public ProductResponseDTO toDTO(Product product) {

        return new ProductResponseDTO(
                product.getId(),
                product.getName(),
                product.getDescription(),
                product.getImage());

    }


    public Product toEntity(ProductRequestDTO productRequestDTO) {

        Product product = new Product();
        product.setName(productRequestDTO.name());
        product.setDescription(productRequestDTO.description());
        product.setImage(productRequestDTO.image());

        return product;

    }

}
