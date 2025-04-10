package com.example.product_catalog.services;


import com.example.product_catalog.domain.dtos.ProductResponseDTO;
import com.example.product_catalog.repositorys.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GetProductByIdService {
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private ProductDTOMapperService dtoMapperService;

    public ProductResponseDTO execute(Long id){
        return dtoMapperService.toDTO(productRepository.findById(id)
                .orElseThrow(()->{
                    return new RuntimeException("PRODUCT NOT FOUND");
                }));
    }

}
