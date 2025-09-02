package com.example.product_catalog.domain.dtos;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Detalhe de erro de validação")
public record ValidationErrorDTO(
        @Schema(description = "Campo que possui o erro", example = "name") String field,

        @Schema(description = "Valor que foi rejeitado", example = "") String rejectedValue,

        @Schema(description = "Mensagem de erro", example = "Nome é obrigatório") String message) {
}
