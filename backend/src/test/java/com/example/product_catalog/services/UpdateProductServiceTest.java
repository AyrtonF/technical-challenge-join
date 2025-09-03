package com.example.product_catalog.services;

import com.example.product_catalog.domain.dtos.ProductResponseDTO;
import com.example.product_catalog.domain.dtos.ProductUpdateDTO;
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
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UpdateProductServiceTest {

    @Mock
    private ProductRepository productRepository;

    @Mock
    private ProductDTOMapperService dtoMapperService;

    @InjectMocks
    private UpdateProductService updateProductService;

    @Test
    void shouldUpdateProductSuccessfully() {
        Long productId = 1L;

        ProductUpdateDTO updateDTO = new ProductUpdateDTO(
                "Updated Phone",
                "New description",
                new BigDecimal("1500.00"),
                20,
                "https://example.com/new-image.jpg");

        Product existingProduct = new Product();
        existingProduct.setId(productId);
        existingProduct.setName("Old Phone");

        Product updatedProduct = new Product();
        updatedProduct.setId(productId);
        updatedProduct.setName("Updated Phone");

        ProductResponseDTO expectedResponse = new ProductResponseDTO(
                productId, "Updated Phone", "New description",
                new BigDecimal("1500.00"), 20, "https://example.com/new-image.jpg");

        when(productRepository.findById(productId)).thenReturn(Optional.of(existingProduct));
        when(productRepository.save(any(Product.class))).thenReturn(updatedProduct);
        when(dtoMapperService.toDTO(updatedProduct)).thenReturn(expectedResponse);

        ProductResponseDTO result = updateProductService.execute(updateDTO, productId);

        assertEquals("Updated Phone", result.name());
        assertEquals(new BigDecimal("1500.00"), result.price());
        assertEquals(20, result.quantity());
    }

    @Test
    void shouldThrowExceptionWhenProductToUpdateNotFound() {
        Long nonExistentId = 999L;
        ProductUpdateDTO updateDTO = new ProductUpdateDTO(
                "Any Name", "Any Description", BigDecimal.ONE, 1, null);

        when(productRepository.findById(nonExistentId)).thenReturn(Optional.empty());

        assertThrows(ProductNotFoundException.class,
                () -> updateProductService.execute(updateDTO, nonExistentId));
    }
}
