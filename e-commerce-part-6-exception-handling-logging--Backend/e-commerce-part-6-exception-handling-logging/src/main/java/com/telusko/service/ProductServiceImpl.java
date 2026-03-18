//package com.telusko.service;
//
//import com.telusko.model.Product;
//import com.telusko.repo.ProductRepo;
//import com.telusko.utils.ImageUtils;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//import org.springframework.web.multipart.MultipartFile;
//
//import java.util.List;
//import java.util.Map;
//
//@Service
//public class ProductServiceImpl implements ProductService {
//
//    @Autowired
//    private ProductRepo productRepo;
//
//    @Override
//    public List<Product> getAllProducts() {
//        return productRepo.findAll();
//    }
//
//    @Override
//    public Product getProductById(Integer id) {
//        return productRepo.findById(id).orElse(null);
//    }
//
//
//    @Override
//    public Product createProduct(Product product, MultipartFile imageFile) throws Exception {
//        if (imageFile != null && !imageFile.isEmpty()) {
//            product.setImageName(imageFile.getOriginalFilename());
//            product.setImageType(imageFile.getContentType());
//            product.setImageData(ImageUtils.compressImage(imageFile.getBytes()));
//        }
//        return productRepo.save(product);
//    }
//
//    @Override
//    public Product updateProduct(Integer id, Product product, MultipartFile imageFile) throws Exception {
//        Product existingProduct = productRepo.findById(id)
//                .orElseThrow(() -> new Exception("Product not found with id: " + id));
//
//        existingProduct.setName(product.getName());
//        existingProduct.setDescription(product.getDescription());
//        existingProduct.setBrand(product.getBrand());
//        existingProduct.setPrice(product.getPrice());
//        existingProduct.setCategory(product.getCategory());
//        existingProduct.setReleaseDate(product.getReleaseDate());
//        existingProduct.setProductAvailable(product.isProductAvailable());
//        existingProduct.setStockQuantity(product.getStockQuantity());
//
//        if (imageFile != null && !imageFile.isEmpty()) {
//            existingProduct.setImageName(imageFile.getOriginalFilename());
//            existingProduct.setImageType(imageFile.getContentType());
//            existingProduct.setImageData(ImageUtils.compressImage(imageFile.getBytes()));
//        }
//
//        return productRepo.save(existingProduct);
//    }
//
//    @Override
//    public void deleteProduct(Integer id) {
//        productRepo.deleteById(id);
//    }
//
//    @Override
//    @Transactional
//    public void checkout(Map<Integer, Integer> productQuantities) throws Exception {
//        for (Map.Entry<Integer, Integer> entry : productQuantities.entrySet()) {
//            Integer productId = entry.getKey();
//            Integer quantity = entry.getValue();
//
//            Product product = productRepo.findById(productId)
//                    .orElseThrow(() -> new Exception("Product not found with id: " + productId));
//
//            if (product.getStockQuantity() < quantity) {
//                throw new Exception("Product " + product.getName() + " is out of stock.");
//            }
//
//            product.setStockQuantity(product.getStockQuantity() - quantity);
//            productRepo.save(product);
//        }
//    }
//}


package com.telusko.service;

import com.telusko.exception.InsufficientStockException;
import com.telusko.exception.ProductCreationException;
import com.telusko.exception.ProductNotFoundException;
import com.telusko.exception.ProductUpdateException;
import com.telusko.model.Product;
import com.telusko.repo.ProductRepo;
import com.telusko.utils.ImageUtils;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class ProductServiceImpl implements ProductService {


    //no need to create logger instance as we are using lombok. it will be automatically done using @SL4fj
   // private static final Logger logger = LoggerFactory.getLogger(ProductServiceImpl.class);

    @Autowired
    private ProductRepo productRepo;

    @Override
    public List<Product> getAllProducts() {
        log.info("Fetching all products");
        return productRepo.findAll();
    }

    @Override
    public Product getProductById(Integer id) {
        log.info("Fetching product with id {}", id);
        return productRepo.findById(id)
                .orElseThrow(() -> new ProductNotFoundException("Product not found with id: " + id));
    }

    @Override
    public Product createProduct(Product product, MultipartFile imageFile) {
        try {
            log.info("Creating product: {}", product.getName());
            if (imageFile != null && !imageFile.isEmpty()) {
                product.setImageName(imageFile.getOriginalFilename());
                product.setImageType(imageFile.getContentType());
                product.setImageData(ImageUtils.compressImage(imageFile.getBytes()));
            }
            return productRepo.save(product);
        } catch (Exception e) {
            throw new ProductCreationException("Failed to create product: " + e.getMessage());
        }
    }

    @Override
    public Product updateProduct(Integer id, Product product, MultipartFile imageFile) throws IOException {
        log.info("Updating product with id {}", id);
        Product existingProduct = productRepo.findById(id)
                .orElseThrow(() -> new ProductNotFoundException("Product not found with id: " + id));

        existingProduct.setName(product.getName());
        existingProduct.setDescription(product.getDescription());
        existingProduct.setBrand(product.getBrand());
        existingProduct.setPrice(product.getPrice());
        existingProduct.setCategory(product.getCategory());
        existingProduct.setReleaseDate(product.getReleaseDate());
        existingProduct.setProductAvailable(product.isProductAvailable());
        existingProduct.setStockQuantity(product.getStockQuantity());

        if (imageFile != null && !imageFile.isEmpty()) {
            existingProduct.setImageName(imageFile.getOriginalFilename());
            existingProduct.setImageType(imageFile.getContentType());
            existingProduct.setImageData(ImageUtils.compressImage(imageFile.getBytes()));
        }

        try {
            return productRepo.save(existingProduct);
        } catch (Exception e) {
            throw new ProductUpdateException("Failed to update product: " + e.getMessage());
        }
    }

    @Override
    public void deleteProduct(Integer id) {
        log.info("Deleting product with id {}", id);
        Product product = productRepo.findById(id)
                .orElseThrow(() -> new ProductNotFoundException("Product not found with id: " + id));
        productRepo.delete(product);
    }

    @Override
    @Transactional
    public void checkout(Map<Integer, Integer> productQuantities) {
        log.info("Performing checkout with product quantities: {}", productQuantities);
        for (Map.Entry<Integer, Integer> entry : productQuantities.entrySet()) {
            Integer productId = entry.getKey();
            Integer quantity = entry.getValue();

            Product product = productRepo.findById(productId)
                    .orElseThrow(() -> new ProductNotFoundException("Product not found with id: " + productId));

            if (product.getStockQuantity() < quantity) {
                throw new InsufficientStockException("Product " + product.getName() + " is out of stock.");
            }

            product.setStockQuantity(product.getStockQuantity() - quantity);
            productRepo.save(product);
        }
    }
}
