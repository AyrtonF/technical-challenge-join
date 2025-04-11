package com.example.product_catalog.domain.dtos;

import java.util.List;

public record PageResponseDTO(
        List<ProductResponseDTO> content,
        int currentPage,
        int totalPages,
        long totalItems) {
}
