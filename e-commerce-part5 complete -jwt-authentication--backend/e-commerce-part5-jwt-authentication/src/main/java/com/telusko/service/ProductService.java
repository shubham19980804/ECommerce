package com.telusko.service;

import com.telusko.model.Product;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public interface ProductService {
    List<Product> getAllProducts();
    Product getProductById(Integer id);
    Product createProduct(Product product, MultipartFile imageFile) throws Exception;
    Product updateProduct(Integer id, Product product, MultipartFile imageFile) throws Exception;
    void deleteProduct(Integer id);
    List<Product> searchProducts(String keyword);
    BigDecimal  checkout(Map<Integer,Integer> productQuantities) throws Exception;
}
