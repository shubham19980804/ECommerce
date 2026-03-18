package com.telusko.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    //no need to create logger instance as we are using lombok. it will be automatically done using @SL4fj
    //private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(ProductNotFoundException.class)
    public ResponseEntity<String> handleProductNotFoundException(ProductNotFoundException ex) {
        log.error("ProductNotFoundException caught: {}", ex.getMessage());
        return ResponseEntity.status(404).body(ex.getMessage());
    }

    @ExceptionHandler(InsufficientStockException.class)
    public ResponseEntity<String> handleInsufficientStockException(InsufficientStockException ex) {
        log.error("InsufficientStockException caught: {}", ex.getMessage());
        return ResponseEntity.status(400).body(ex.getMessage());
    }

    @ExceptionHandler(ProductCreationException.class)
    public ResponseEntity<String> handleProductCreationException(ProductCreationException ex) {
        log.error("ProductCreationException caught: {}", ex.getMessage());
        return ResponseEntity.status(500).body(ex.getMessage());
    }

    @ExceptionHandler(ProductUpdateException.class)
    public ResponseEntity<String> handleProductUpdateException(ProductUpdateException ex) {
        log.error("ProductUpdateException caught: {}", ex.getMessage());
        return ResponseEntity.status(500).body(ex.getMessage());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleAllExceptions(Exception ex) {
        log.error("Unexpected error occurred: {}", ex.getMessage());
        return ResponseEntity.status(500).body("An unexpected error occurred: " + ex.getMessage());
    }
}
