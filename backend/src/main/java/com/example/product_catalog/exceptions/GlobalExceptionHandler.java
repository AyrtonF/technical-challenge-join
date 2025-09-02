package com.example.product_catalog.exceptions;

import com.example.product_catalog.domain.dtos.ErrorResponseDTO;
import com.example.product_catalog.domain.dtos.ValidationErrorDTO;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.util.ArrayList;
import java.util.List;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(ProductNotFoundException.class)
    @ApiResponse(responseCode = "404", description = "Produto não encontrado", content = @Content(schema = @Schema(implementation = ErrorResponseDTO.class)))
    public ResponseEntity<ErrorResponseDTO> handleProductNotFound(
            ProductNotFoundException ex,
            HttpServletRequest request) {

        logger.warn("Produto não encontrado: {}", ex.getMessage());

        ErrorResponseDTO errorResponse = new ErrorResponseDTO(
                "PRODUCT_NOT_FOUND",
                ex.getMessage(),
                request.getRequestURI());

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ApiResponse(responseCode = "400", description = "Dados de entrada inválidos", content = @Content(schema = @Schema(implementation = ErrorResponseDTO.class)))
    public ResponseEntity<ErrorResponseDTO> handleValidationException(
            MethodArgumentNotValidException ex,
            HttpServletRequest request) {

        logger.warn("Erro de validação: {}", ex.getMessage());

        List<ValidationErrorDTO> validationErrors = new ArrayList<>();

        // Extrai os erros de validação
        for (FieldError fieldError : ex.getBindingResult().getFieldErrors()) {
            ValidationErrorDTO validationError = new ValidationErrorDTO(
                    fieldError.getField(),
                    fieldError.getRejectedValue() != null ? fieldError.getRejectedValue().toString() : "null",
                    fieldError.getDefaultMessage());
            validationErrors.add(validationError);
        }

        ErrorResponseDTO errorResponse = new ErrorResponseDTO(
                "VALIDATION_ERROR",
                "Dados de entrada inválidos",
                validationErrors,
                request.getRequestURI());

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }

    @ExceptionHandler(ValidationException.class)
    @ApiResponse(responseCode = "400", description = "Erro de validação", content = @Content(schema = @Schema(implementation = ErrorResponseDTO.class)))
    public ResponseEntity<ErrorResponseDTO> handleValidationException(
            ValidationException ex,
            HttpServletRequest request) {

        logger.warn("Erro de validação customizada: {}", ex.getMessage());

        ErrorResponseDTO errorResponse = new ErrorResponseDTO(
                "VALIDATION_ERROR",
                ex.getMessage(),
                request.getRequestURI());

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }

    @ExceptionHandler(ProductAlreadyExistsException.class)
    @ApiResponse(responseCode = "409", description = "Produto já existe", content = @Content(schema = @Schema(implementation = ErrorResponseDTO.class)))
    public ResponseEntity<ErrorResponseDTO> handleProductAlreadyExists(
            ProductAlreadyExistsException ex,
            HttpServletRequest request) {

        logger.warn("Produto já existe: {}", ex.getMessage());

        ErrorResponseDTO errorResponse = new ErrorResponseDTO(
                "PRODUCT_ALREADY_EXISTS",
                ex.getMessage(),
                request.getRequestURI());

        return ResponseEntity.status(HttpStatus.CONFLICT).body(errorResponse);
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    @ApiResponse(responseCode = "400", description = "Tipo de argumento inválido", content = @Content(schema = @Schema(implementation = ErrorResponseDTO.class)))
    public ResponseEntity<ErrorResponseDTO> handleTypeMismatch(
            MethodArgumentTypeMismatchException ex,
            HttpServletRequest request) {

        logger.warn("Tipo de argumento inválido: {}", ex.getMessage());

        String message = String.format(
                "Parâmetro '%s' deve ser do tipo %s",
                ex.getName(),
                ex.getRequiredType().getSimpleName());

        ErrorResponseDTO errorResponse = new ErrorResponseDTO(
                "INVALID_PARAMETER_TYPE",
                message,
                request.getRequestURI());

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }


    @ExceptionHandler(Exception.class)
    @ApiResponse(responseCode = "500", description = "Erro interno do servidor", content = @Content(schema = @Schema(implementation = ErrorResponseDTO.class)))
    public ResponseEntity<ErrorResponseDTO> handleGenericException(
            Exception ex,
            HttpServletRequest request) {

        logger.error("Erro interno não tratado: ", ex);

        ErrorResponseDTO errorResponse = new ErrorResponseDTO(
                "INTERNAL_SERVER_ERROR",
                "Erro interno do servidor. Tente novamente mais tarde.",
                request.getRequestURI());

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
    }

    @ExceptionHandler(RuntimeException.class)
    @ApiResponse(responseCode = "500", description = "Erro de execução", content = @Content(schema = @Schema(implementation = ErrorResponseDTO.class)))
    public ResponseEntity<ErrorResponseDTO> handleRuntimeException(
            RuntimeException ex,
            HttpServletRequest request) {

        logger.error("Erro de execução: ", ex);

        ErrorResponseDTO errorResponse = new ErrorResponseDTO(
                "RUNTIME_ERROR",
                "Erro inesperado durante a execução da operação",
                request.getRequestURI());

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
    }
}
