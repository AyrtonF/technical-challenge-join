package com.example.product_catalog.domain.dtos;

import java.math.BigDecimal;

public record ProductUpdateDTO(
        String name,
        String description,
        BigDecimal price,
        Integer quantity,
        String imageUrl) {
}
