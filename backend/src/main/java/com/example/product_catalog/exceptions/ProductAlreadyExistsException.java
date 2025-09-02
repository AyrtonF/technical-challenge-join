package com.example.product_catalog.exceptions;

public class ProductAlreadyExistsException extends RuntimeException {
    public ProductAlreadyExistsException(String message) {
        super(message);
    }

    public ProductAlreadyExistsException(String message, Throwable cause) {
        super(message, cause);
    }

    public static ProductAlreadyExistsException forProductName(String productName) {
        return new ProductAlreadyExistsException("Produto com nome '" + productName + "' já existe");
    }
}
