package com.telusko.exception;

public class ProductCreationException extends RuntimeException {
    public ProductCreationException(String message) {
        super(message);
    }
}