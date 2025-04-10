package com.example.product_catalog.controllers;


import com.example.product_catalog.domain.dtos.ProductRequestDTO;
import com.example.product_catalog.domain.dtos.ProductResponseDTO;
import com.example.product_catalog.domain.dtos.ProductUpdateDTO;
import com.example.product_catalog.domain.models.Product;
import com.example.product_catalog.repositorys.ProductRepository;
import com.example.product_catalog.services.*;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {
    @Autowired
    private CreateProductService createProductService;
    @Autowired
    private GetAllProductsService getAllProductsService;
    @Autowired
    private GetProductByIdService getProductByIdService;
    @Autowired
    private UpdateProductService updateProductService;
    @Autowired
    private DeleteProductByIdService deleteProductByIdService;

    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @PostMapping
    public ResponseEntity<ProductResponseDTO> create(@RequestBody ProductRequestDTO productRequestDTO){
        return ResponseEntity.status(HttpStatus.CREATED).body(createProductService.execute(productRequestDTO));
    }

    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @GetMapping
    public ResponseEntity<List<ProductResponseDTO>> getAll(){
        return ResponseEntity.status(HttpStatus.OK).body(getAllProductsService.execute());
    }

    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @GetMapping("/{id}")
    public ResponseEntity<ProductResponseDTO> getById(@PathVariable("id") Long id){
        return ResponseEntity.status(HttpStatus.OK).body(getProductByIdService.execute(id));
    }

    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @PutMapping("/{id}")
    public ResponseEntity<ProductResponseDTO> update(@RequestBody ProductUpdateDTO productUpdateDTO, @PathVariable("id") Long id){
        return ResponseEntity.status(HttpStatus.OK).body(updateProductService.execute(productUpdateDTO, id));
    }

    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteById(@PathVariable("id") Long id){
        deleteProductByIdService.execute(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

}
