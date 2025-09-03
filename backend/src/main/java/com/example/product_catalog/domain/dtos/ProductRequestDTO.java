package com.example.product_catalog.domain.dtos;

import jakarta.validation.constraints.*;
import java.math.BigDecimal;

public record ProductRequestDTO(
                @NotBlank(message = "Nome é obrigatório") @Size(min = 3, max = 100, message = "Nome deve ter entre 3 e 100 caracteres") String name,
                String description,
                @DecimalMin(value = "0.01", message = "Preço deve ser maior que zero") @Digits(integer = 8, fraction = 2, message = "Preço inválido") BigDecimal price,
                @Min(value = 0, message = "Quantidade não pode ser negativa") int quantity,
                String imageUrl) {
}
