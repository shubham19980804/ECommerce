package com.telusko.service;

import com.telusko.model.Product;
import com.telusko.repo.ProductRepo;
import com.telusko.utils.ImageUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepo productRepo;

    @Override
    public List<Product> getAllProducts() {
        return productRepo.findAll();
    }

    @Override
    public Product getProductById(Integer id) {
        return productRepo.findById(id).orElse(null);
    }


    @Override
    public Product createProduct(Product product, MultipartFile imageFile) throws Exception {
        if (imageFile != null && !imageFile.isEmpty()) {
            product.setImageName(imageFile.getOriginalFilename());
            product.setImageType(imageFile.getContentType());
            product.setImageData(ImageUtils.compressImage(imageFile.getBytes()));
        }
        return productRepo.save(product);
    }

    @Override
    public Product updateProduct(Integer id, Product product, MultipartFile imageFile) throws Exception {
        Product existingProduct = productRepo.findById(id)
                .orElseThrow(() -> new Exception("Product not found with id: " + id));

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

        return productRepo.save(existingProduct);
    }

    @Override
    public void deleteProduct(Integer id) {
        productRepo.deleteById(id);
    }
    @Override
    public List<Product> searchProducts(String keyword) {
        return productRepo.searchProducts(keyword);
    }


    @Override
    @Transactional
    public BigDecimal checkout(Map<Integer, Integer> productQuantities) throws Exception {
        BigDecimal totalPrice = BigDecimal.ZERO;

        for (Map.Entry<Integer, Integer> entry : productQuantities.entrySet()) {
            Integer productId = entry.getKey();
            Integer quantity = entry.getValue();

            Product product = productRepo.findById(productId)
                    .orElseThrow(() -> new Exception("Product not found with id: " + productId));

            if (product.getStockQuantity() < quantity) {
                throw new Exception("Product " + product.getName() + " is out of stock.");
            }

            BigDecimal productTotalPrice = product.getPrice().multiply(BigDecimal.valueOf(quantity));
            totalPrice = totalPrice.add(productTotalPrice);

            product.setStockQuantity(product.getStockQuantity() - quantity);
            productRepo.save(product);
        }

        return totalPrice;
    }

}
