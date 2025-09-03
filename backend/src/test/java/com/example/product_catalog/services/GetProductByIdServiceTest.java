package com.example.product_catalog.services;

import com.example.product_catalog.domain.dtos.ProductResponseDTO;
import com.example.product_catalog.domain.models.Product;
import com.example.product_catalog.exceptions.ProductNotFoundException;
import com.example.product_catalog.repositorys.ProductRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GetProductByIdServiceTest {

    @Mock
    private ProductRepository productRepository;

    @Mock
    private ProductDTOMapperService dtoMapperService;

    @InjectMocks
    private GetProductByIdService getProductByIdService;

    @Test
    void shouldReturnProductWhenIdExists() {
        Long productId = 1L;

        Product product = new Product();
        product.setId(productId);
        product.setName("Tablet");

        ProductResponseDTO expectedResponse = new ProductResponseDTO(
                productId, "Tablet", "10-inch tablet", new BigDecimal("800.00"), 12, null);

        when(productRepository.findById(productId)).thenReturn(Optional.of(product));
        when(dtoMapperService.toDTO(product)).thenReturn(expectedResponse);

        ProductResponseDTO result = getProductByIdService.execute(productId);

        assertEquals(productId, result.id());
        assertEquals("Tablet", result.name());
    }

    @Test
    void shouldThrowExceptionWhenProductNotFound() {
        Long nonExistentId = 999L;

        when(productRepository.findById(nonExistentId)).thenReturn(Optional.empty());

        assertThrows(ProductNotFoundException.class,
                () -> getProductByIdService.execute(nonExistentId));
    }
}
