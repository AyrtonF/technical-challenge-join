package com.example.product_catalog.services;

import com.example.product_catalog.domain.dtos.ProductResponseDTO;
import com.example.product_catalog.domain.models.Product;
import com.example.product_catalog.repositorys.ProductRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GetAllProductsServiceTest {

    @Mock
    private ProductRepository productRepository;

    @Mock
    private ProductDTOMapperService dtoMapperService;

    @InjectMocks
    private GetAllProductsService getAllProductsService;

    @Test
    void shouldReturnAllProductsWhenNoSearchTerm() {
        Pageable pageable = PageRequest.of(0, 10);

        Product product = new Product();
        product.setId(1L);
        product.setName("Notebook");

        ProductResponseDTO responseDTO = new ProductResponseDTO(
                1L, "Notebook", "Gaming laptop", new BigDecimal("3500.00"), 3, null);

        Page<Product> productPage = new PageImpl<>(List.of(product));

        when(productRepository.findAll(pageable)).thenReturn(productPage);
        when(dtoMapperService.toDTO(product)).thenReturn(responseDTO);

        Page<ProductResponseDTO> result = getAllProductsService.execute(null, pageable);

        assertEquals(1, result.getTotalElements());
        assertEquals("Notebook", result.getContent().get(0).name());
    }

    @Test
    void shouldReturnFilteredProductsWhenSearchTermProvided() {
        Pageable pageable = PageRequest.of(0, 10);
        String searchTerm = "phone";

        Product product = new Product();
        product.setId(2L);
        product.setName("Smartphone");

        ProductResponseDTO responseDTO = new ProductResponseDTO(
                2L, "Smartphone", "Latest model", new BigDecimal("1200.00"), 8, null);

        Page<Product> productPage = new PageImpl<>(List.of(product));

        when(productRepository.findByNameContainingIgnoreCase(searchTerm, pageable)).thenReturn(productPage);
        when(dtoMapperService.toDTO(product)).thenReturn(responseDTO);

        Page<ProductResponseDTO> result = getAllProductsService.execute(searchTerm, pageable);

        assertEquals(1, result.getTotalElements());
        assertEquals("Smartphone", result.getContent().get(0).name());
    }
}
