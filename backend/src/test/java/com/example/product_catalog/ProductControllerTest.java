package com.example.product_catalog;

import com.example.product_catalog.controllers.ProductController;
import com.example.product_catalog.domain.dtos.ProductRequestDTO;
import com.example.product_catalog.domain.dtos.ProductResponseDTO;
import com.example.product_catalog.domain.dtos.ProductUpdateDTO;
import com.example.product_catalog.services.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.Arrays;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ProductController.class)
class ProductControllerTest {

        @Autowired
        private MockMvc mockMvc;

        @Autowired
        private ObjectMapper objectMapper;

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

        @Test
        void shouldCreateProductAndReturnCreatedStatus() throws Exception {
                ProductRequestDTO request = new ProductRequestDTO(
                                "Gaming Mouse",
                                "RGB wireless mouse",
                                new BigDecimal("199.99"),
                                15,
                                "https://example.com/mouse.jpg");

                ProductResponseDTO response = new ProductResponseDTO(
                                1L, "Gaming Mouse", "RGB wireless mouse",
                                new BigDecimal("199.99"), 15, "https://example.com/mouse.jpg");

                when(createProductService.execute(any(ProductRequestDTO.class))).thenReturn(response);

                mockMvc.perform(post("/products")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(request)))
                                .andExpect(status().isCreated())
                                .andExpect(jsonPath("$.name").value("Gaming Mouse"))
                                .andExpect(jsonPath("$.price").value(199.99));
        }

        @Test
        void shouldReturnProductWhenValidIdProvided() throws Exception {
                Long productId = 1L;
                ProductResponseDTO response = new ProductResponseDTO(
                                productId, "Headset", "Gaming headset",
                                new BigDecimal("350.00"), 8, null);

                when(getProductByIdService.execute(productId)).thenReturn(response);

                mockMvc.perform(get("/products/{id}", productId))
                                .andExpect(status().isOk())
                                .andExpect(jsonPath("$.id").value(1))
                                .andExpect(jsonPath("$.name").value("Headset"));
        }

        @Test
        void shouldReturnAllProductsWithPagination() throws Exception {
                ProductResponseDTO product1 = new ProductResponseDTO(
                                1L, "Keyboard", "Mechanical keyboard",
                                new BigDecimal("250.00"), 5, null);
                ProductResponseDTO product2 = new ProductResponseDTO(
                                2L, "Monitor", "4K gaming monitor",
                                new BigDecimal("800.00"), 3, null);

                Page<ProductResponseDTO> productPage = new PageImpl<>(Arrays.asList(product1, product2));
                when(getAllProductsService.execute(any(), any())).thenReturn(productPage);

                mockMvc.perform(get("/products"))
                                .andExpect(status().isOk())
                                .andExpect(jsonPath("$.content").isArray())
                                .andExpect(jsonPath("$.content[0].name").value("Keyboard"));
        }

        @Test
        void shouldUpdateProductSuccessfully() throws Exception {
                Long productId = 1L;
                ProductUpdateDTO updateRequest = new ProductUpdateDTO(
                                "Updated Mouse",
                                "Updated description",
                                new BigDecimal("250.00"),
                                20,
                                "https://example.com/updated-mouse.jpg");

                ProductResponseDTO updatedResponse = new ProductResponseDTO(
                                productId, "Updated Mouse", "Updated description",
                                new BigDecimal("250.00"), 20, "https://example.com/updated-mouse.jpg");

                when(updateProductService.execute(any(ProductUpdateDTO.class), eq(productId)))
                                .thenReturn(updatedResponse);

                mockMvc.perform(put("/products/{id}", productId)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(updateRequest)))
                                .andExpect(status().isOk())
                                .andExpect(jsonPath("$.name").value("Updated Mouse"))
                                .andExpect(jsonPath("$.price").value(250.00));
        }

        @Test
        void shouldDeleteProductAndReturnNoContent() throws Exception {
                Long productId = 1L;

                mockMvc.perform(delete("/products/{id}", productId))
                                .andExpect(status().isNoContent());
        }
}
