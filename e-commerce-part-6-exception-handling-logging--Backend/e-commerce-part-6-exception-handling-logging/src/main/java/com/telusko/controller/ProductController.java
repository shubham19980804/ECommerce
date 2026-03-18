//package com.telusko.controller;
//
//import com.telusko.model.Product;
//import com.telusko.service.ProductService;
//import com.telusko.utils.ImageUtils;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.MediaType;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//import org.springframework.web.multipart.MultipartFile;
//
//import java.util.List;
//import java.util.Map;
//
//@RestController
//@CrossOrigin
//@RequestMapping("/api")
//public class ProductController {
//
//    @Autowired
//    private ProductService productService;
//
//    @GetMapping("/products")
//    public ResponseEntity<List<Product>> getAllProducts() {
//        List<Product> productList = productService.getAllProducts();
//        return new ResponseEntity<>(productList, HttpStatus.OK);
//    }
//
//    @GetMapping("/product/{id}")
//    public ResponseEntity<Product> getProductById(@PathVariable Integer id) {
//        Product product = productService.getProductById(id);
//        if (product != null) {
//            return new ResponseEntity<>(product, HttpStatus.OK);
//        } else {
//            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//        }
//    }
//
//
//    @PostMapping("/product")
//    public ResponseEntity<?> createProduct(@RequestPart("product") Product product,
//                                           @RequestPart("imageFile") MultipartFile imageFile) {
//        try {
//            System.out.println(product.isProductAvailable());
//            System.out.println("actual image size is: " + imageFile.getBytes().length);
//            Product savedProduct = productService.createProduct(product, imageFile);
//
//            //size after compression
//            System.out.println("the image size after compression is " + savedProduct.getImageData().length);
//            return new ResponseEntity<>(savedProduct, HttpStatus.CREATED);
//        } catch (Exception e) {
//            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
//        }
//    }
//
//    @GetMapping("/product/{productId}/image")
//    public ResponseEntity<byte[]> getImageByProductId(@PathVariable Integer productId) {
//        Product product = productService.getProductById(productId);
//        if (product != null && product.getImageData() != null) {
//            byte[] imageData = ImageUtils.decompressImage(product.getImageData());
//
//            //size after decompression
//            System.out.println("the image size after decompression is " + imageData.length);
//
//            return ResponseEntity.ok()
//                    .contentType(MediaType.valueOf(product.getImageType()))
//                    .body(imageData);
//        } else {
//            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//        }
//    }
//
//
//    @PutMapping("/product/{id}")
//    public ResponseEntity<String> updateProduct(@PathVariable Integer id,
//                                                @RequestPart("product") Product product,
//                                                @RequestPart("imageFile") MultipartFile imageFile) throws Exception {
//        Product updatedProduct = productService.updateProduct(id, product, imageFile);
//        if (updatedProduct != null) {
//            return new ResponseEntity<>("Product updated successfully", HttpStatus.OK);
//        } else {
//            return new ResponseEntity<>("Failed to update product", HttpStatus.BAD_REQUEST);
//        }
//    }
//
//
//    @DeleteMapping("/product/{id}")
//    public ResponseEntity<String> deleteProduct(@PathVariable Integer id) {
//        Product product = productService.getProductById(id);
//        if (product != null) {
//            productService.deleteProduct(id);
//            return new ResponseEntity<>("Product deleted successfully", HttpStatus.OK);
//        } else {
//            return new ResponseEntity<>("Product not found", HttpStatus.NOT_FOUND);
//        }
//    }
//
//
//
//    // Method to handle checkout functionality
//    @PostMapping("/checkout")
//    public ResponseEntity<String> checkout(@RequestBody Map<Integer, Integer> productQuantities) {
//        try {
//            productService.checkout(productQuantities);
//            return new ResponseEntity<>("Checkout successful", HttpStatus.OK);
//        } catch (Exception e) {
//            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
//        }
//    }
//
//}

package com.telusko.controller;

import com.telusko.model.Product;
import com.telusko.service.ProductService;
import com.telusko.utils.ImageUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin
@RequestMapping("/api")
@Slf4j
public class ProductController {


    //no need to create logger instance as we are using lombok. it will be automatically done using @SL4fj
    //private static final Logger logger = LoggerFactory.getLogger(ProductController.class);

    @Autowired
    private ProductService productService;

    @GetMapping("/products")
    public ResponseEntity<List<Product>> getAllProducts() {
        log.info("Fetching all products");
        List<Product> productList = productService.getAllProducts();
        return new ResponseEntity<>(productList, HttpStatus.OK);
    }

    @GetMapping("/product/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable Integer id) {
        log.info("Fetching product with id {}", id);
        Product product = productService.getProductById(id);
        return new ResponseEntity<>(product, HttpStatus.OK);
    }

    @PostMapping("/product")
    public ResponseEntity<Product> createProduct(@RequestPart("product") Product product,
                                                 @RequestPart("imageFile") MultipartFile imageFile) throws Exception {
        log.info("Creating product: {}", product.getName());
        Product savedProduct = productService.createProduct(product, imageFile);
        return new ResponseEntity<>(savedProduct, HttpStatus.CREATED);
    }

    @GetMapping("/product/{productId}/image")
    public ResponseEntity<byte[]> getImageByProductId(@PathVariable Integer productId) {
        log.info("Fetching image for product with id {}", productId);
        Product product = productService.getProductById(productId);
        byte[] imageData = ImageUtils.decompressImage(product.getImageData());
        return ResponseEntity.ok()
                .contentType(MediaType.valueOf(product.getImageType()))
                .body(imageData);
    }

    @PutMapping("/product/{id}")
    public ResponseEntity<String> updateProduct(@PathVariable Integer id,
                                                @RequestPart("product") Product product,
                                                @RequestPart("imageFile") MultipartFile imageFile) throws Exception {
        log.info("Updating product with id {}", id);
        productService.updateProduct(id, product, imageFile);
        return new ResponseEntity<>("Product updated successfully", HttpStatus.OK);
    }

    @DeleteMapping("/product/{id}")
    public ResponseEntity<String> deleteProduct(@PathVariable Integer id) {
        log.info("Deleting product with id {}", id);
        productService.deleteProduct(id);
        return new ResponseEntity<>("Product deleted successfully", HttpStatus.OK);
    }

    @PostMapping("/checkout")
    public ResponseEntity<String> checkout(@RequestBody Map<Integer, Integer> productQuantities) throws Exception {
        log.info("Performing checkout with product quantities: {}", productQuantities);
        productService.checkout(productQuantities);
        return new ResponseEntity<>("Checkout successful", HttpStatus.OK);
    }
}
