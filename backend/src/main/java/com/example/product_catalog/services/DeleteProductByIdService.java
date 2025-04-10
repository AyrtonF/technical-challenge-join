package com.example.product_catalog.services;


import com.example.product_catalog.repositorys.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DeleteProductByIdService {

    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private ProductDTOMapperService dtoMapperService;

    public void execute(Long id){

        if(!productRepository.existsById(id)){
            throw new RuntimeException("PRODUCT NOT FOUND");
        }
        this.productRepository.deleteById(id);

    }

}
