package com.example.product_catalog;

import com.example.product_catalog.controllers.ProductController;
import com.example.product_catalog.domain.dtos.ProductRequestDTO;
import com.example.product_catalog.domain.dtos.ProductResponseDTO;
import com.example.product_catalog.services.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;

import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import static org.mockito.Mockito.when;

@WebMvcTest(ProductController.class)
class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CreateProductService createProductService;

    @MockBean
    private GetAllProductsService getAllProductsService;

    @MockBean
    private GetProductByIdService getProductByIdService;

    @MockBean
    private UpdateProductService updateProductService;

    @MockBean
    private DeleteProductByIdService deleteProductByIdService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void shouldCreateProduct() throws Exception {
        ProductRequestDTO requestDTO = new ProductRequestDTO(
                "Notebook", "Ultra rápido", new BigDecimal("3000.00"), 10, "Informática"
        );

        ProductResponseDTO responseDTO = new ProductResponseDTO(
                1L, "Notebook", "Ultra rápido", new BigDecimal("3000.00"), 10, "Informática"
        );

        when(createProductService.execute(requestDTO)).thenReturn(responseDTO);

        mockMvc.perform(post("/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.name").value("Notebook"));
    }
}


