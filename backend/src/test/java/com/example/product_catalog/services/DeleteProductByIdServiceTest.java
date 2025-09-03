package com.example.product_catalog.services;

import com.example.product_catalog.exceptions.ProductNotFoundException;
import com.example.product_catalog.repositorys.ProductRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class DeleteProductByIdServiceTest {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private DeleteProductByIdService deleteProductByIdService;

    @Test
    void shouldDeleteProductWhenIdExists() {
        Long productId = 1L;

        when(productRepository.existsById(productId)).thenReturn(true);

        deleteProductByIdService.execute(productId);

        verify(productRepository).deleteById(productId);
    }

    @Test
    void shouldThrowExceptionWhenProductToDeleteNotFound() {
        Long nonExistentId = 999L;

        when(productRepository.existsById(nonExistentId)).thenReturn(false);

        assertThrows(ProductNotFoundException.class,
                () -> deleteProductByIdService.execute(nonExistentId));
    }
}
