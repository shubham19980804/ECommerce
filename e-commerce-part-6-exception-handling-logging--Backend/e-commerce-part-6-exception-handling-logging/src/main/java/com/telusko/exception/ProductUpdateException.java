package com.telusko.exception;

public class ProductUpdateException extends RuntimeException {
    public ProductUpdateException(String message) {
        super(message);
    }
}