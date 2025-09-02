package com.example.product_catalog.domain.dtos;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;
import java.util.List;

@Schema(description = "Resposta padrão para erros da API")
public record ErrorResponseDTO(
        @Schema(description = "Código do erro", example = "PRODUCT_NOT_FOUND") String code,

        @Schema(description = "Mensagem do erro", example = "Produto com ID 1 não foi encontrado") String message,

        @Schema(description = "Lista de detalhes dos erros de validação") List<ValidationErrorDTO> validationErrors,

        @Schema(description = "Timestamp do erro") @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime timestamp,

        @Schema(description = "Path da requisição que gerou o erro", example = "/products/1") String path) {
    public ErrorResponseDTO(String code, String message, String path) {
        this(code, message, null, LocalDateTime.now(), path);
    }

    public ErrorResponseDTO(String code, String message, List<ValidationErrorDTO> validationErrors, String path) {
        this(code, message, validationErrors, LocalDateTime.now(), path);
    }
}
