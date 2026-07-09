package com.georgeCross.george.controllers;

import com.georgeCross.george.models.Product;
import com.georgeCross.george.repositories.ProductRepository;

import com.georgeCross.george.service.ImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:5173")
@RequiredArgsConstructor
public class ShopController {

    private final ProductRepository productRepository;
    private final ImageService imageService;

    @GetMapping("/products")
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    @GetMapping("/products/{id}")
    public Product getProductById(@PathVariable("id") Long id) {
        return productRepository.findById(id).orElse(null);
    }

    @PostMapping("/products")
    public Product createProduct(@ModelAttribute Product product, @RequestParam("file") MultipartFile[] file) {

        Product savedProduct = productRepository.save(product);

        if (file != null && file.length > 0) {
            List<String> cloudImageUrls = imageService.uploadProductImage(file, savedProduct.getId());
            savedProduct.setImageUrls(cloudImageUrls);
            savedProduct = productRepository.save(savedProduct);
        }

        return savedProduct;
    }
}
