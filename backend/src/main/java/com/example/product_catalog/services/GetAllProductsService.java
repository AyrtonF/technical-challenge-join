package com.example.product_catalog.services;

import com.example.product_catalog.domain.dtos.ProductResponseDTO;
import com.example.product_catalog.domain.models.Product;
import com.example.product_catalog.repositorys.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class GetAllProductsService {

    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private ProductDTOMapperService dtoMapperService;

    public List<ProductResponseDTO> execute(){

        return productRepository.findAll().stream().map(product -> {
            return dtoMapperService.toDTO(product);
        }).collect(Collectors.toList());

    }



}
