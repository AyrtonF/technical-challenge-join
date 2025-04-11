package com.example.product_catalog.domain.dtos;

import java.math.BigDecimal;

public record ProductRequestDTO(
        String name,
        String description,
        BigDecimal price,
        int quantity,
        String imageUrl) {
}
