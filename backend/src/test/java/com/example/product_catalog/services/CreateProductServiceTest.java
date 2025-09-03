package com.example.product_catalog.services;

import com.example.product_catalog.domain.dtos.ProductRequestDTO;
import com.example.product_catalog.domain.dtos.ProductResponseDTO;
import com.example.product_catalog.domain.models.Product;
import com.example.product_catalog.repositorys.ProductRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CreateProductServiceTest {

    @Mock
    private ProductRepository productRepository;

    @Mock
    private ProductDTOMapperService dtoMapperService;

    @InjectMocks
    private CreateProductService createProductService;

    @Test
    void shouldCreateProductSuccessfully() {
        ProductRequestDTO request = new ProductRequestDTO(
                "Smartphone",
                "iPhone 15 Pro",
                new BigDecimal("5999.99"),
                5,
                "https://example.com/iphone.jpg");

        Product entity = new Product();
        Product savedEntity = new Product();
        savedEntity.setId(1L);

        ProductResponseDTO expectedResponse = new ProductResponseDTO(
                1L,
                "Smartphone",
                "iPhone 15 Pro",
                new BigDecimal("5999.99"),
                5,
                "https://example.com/iphone.jpg");

        when(dtoMapperService.toEntity(request)).thenReturn(entity);
        when(productRepository.save(entity)).thenReturn(savedEntity);
        when(dtoMapperService.toDTO(savedEntity)).thenReturn(expectedResponse);

        ProductResponseDTO result = createProductService.execute(request);

        assertNotNull(result);
        assertEquals(1L, result.id());
        assertEquals("Smartphone", result.name());
        assertEquals(new BigDecimal("5999.99"), result.price());
    }
}
