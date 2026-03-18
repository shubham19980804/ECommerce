package com.telusko.controller;

import com.telusko.model.Product;
import com.telusko.service.ProductService;
import com.telusko.utils.ImageUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin
@RequestMapping("/api")
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping("/products")
    public ResponseEntity<List<Product>> getAllProducts() {
        List<Product> productList = productService.getAllProducts();
        return new ResponseEntity<>(productList, HttpStatus.OK);
    }

    @GetMapping("/product/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable Integer id) {
        Product product = productService.getProductById(id);
        if (product != null) {
            return new ResponseEntity<>(product, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }


    @PostMapping("/product")
    public ResponseEntity<?> createProduct(@RequestPart("product") Product product,
                                           @RequestPart("imageFile") MultipartFile imageFile) {
        try {
            System.out.println(product.isProductAvailable());
            System.out.println("actual image size is: "+imageFile.getBytes().length);
            Product savedProduct = productService.createProduct(product, imageFile);

            //size after compression
            System.out.println("the image size after compression is "+savedProduct.getImageData().length);
            return new ResponseEntity<>(savedProduct, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/product/{productId}/image")
    public ResponseEntity<byte[]> getImageByProductId(@PathVariable Integer productId) {
        Product product = productService.getProductById(productId);
        if (product != null && product.getImageData() != null) {
            byte[] imageData = ImageUtils.decompressImage(product.getImageData());

            //size after decompression
            System.out.println("the image size after decompression is "+imageData.length);

            return ResponseEntity.ok()
                    .contentType(MediaType.valueOf(product.getImageType()))
                    .body(imageData);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }


    @PutMapping("/product/{id}")
    public ResponseEntity<String> updateProduct(@PathVariable Integer id,
                                                @RequestPart("product") Product product,
                                                @RequestPart("imageFile") MultipartFile imageFile) throws Exception {
        Product updatedProduct = productService.updateProduct(id, product, imageFile);
        if (updatedProduct != null) {
            return new ResponseEntity<>("Product updated successfully", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Failed to update product", HttpStatus.BAD_REQUEST);
        }
    }


    @DeleteMapping("/product/{id}")
    public ResponseEntity<String> deleteProduct(@PathVariable Integer id) {
        Product product = productService.getProductById(id);
        if (product != null) {
            productService.deleteProduct(id);
            return new ResponseEntity<>("Product deleted successfully", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Product not found", HttpStatus.NOT_FOUND);
        }
    }



    @GetMapping("/products/search")
    public ResponseEntity<List<Product>> searchProducts(@RequestParam String keyword) {
        List<Product> products = productService.searchProducts(keyword);
        return new ResponseEntity<>(products, HttpStatus.OK);
    }


    @PostMapping("/checkout")
    public ResponseEntity<BigDecimal> checkout(@RequestBody Map<Integer, Integer> productQuantities) {
        try {
            BigDecimal totalPrice = productService.checkout(productQuantities);
            return ResponseEntity.ok(totalPrice);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

}
