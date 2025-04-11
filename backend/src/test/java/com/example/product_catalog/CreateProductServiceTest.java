package com.example.product_catalog;

import com.example.product_catalog.domain.dtos.ProductRequestDTO;
import com.example.product_catalog.domain.dtos.ProductResponseDTO;
import com.example.product_catalog.domain.models.Product;
import com.example.product_catalog.repositorys.ProductRepository;
import com.example.product_catalog.services.CreateProductService;
import com.example.product_catalog.services.ProductDTOMapperService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class CreateProductServiceTest {

    @Mock
    private ProductRepository productRepository;

    @Mock
    private ProductDTOMapperService dtoMapperService;

    @InjectMocks
    private CreateProductService createProductService;

    @Test
    void createProductAndReturnProductResponseDTO() {

        ProductRequestDTO requestDTO = new ProductRequestDTO(
                "Notebook", "Notebook gamer", new BigDecimal("4500.00"), 10, "Eletrônicos"
        );

        Product product = new Product();
        Product savedProduct = new Product();

        ProductResponseDTO responseDTO = new ProductResponseDTO(
                1L, "Notebook", "Notebook gamer", new BigDecimal("4500.00"), 10, "Eletrônicos"
        );

        when(dtoMapperService.toEntity(requestDTO)).thenReturn(product);
        when(productRepository.save(product)).thenReturn(savedProduct);
        when(dtoMapperService.toDTO(savedProduct)).thenReturn(responseDTO);

        ProductResponseDTO result = createProductService.execute(requestDTO);

        assertNotNull(result);
        assertEquals(responseDTO, result);
        verify(dtoMapperService).toEntity(requestDTO);
        verify(productRepository).save(product);
        verify(dtoMapperService).toDTO(savedProduct);
    }

}
