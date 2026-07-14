package com.georgeCross.george.controllers;

import com.georgeCross.george.models.Product;
import com.georgeCross.george.repositories.ProductRepository;

import com.georgeCross.george.service.ImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
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

    @DeleteMapping("/products/{id}")
    public ResponseEntity<Void> deleteProductById(@PathVariable("id") Long id) {
        productRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/products/{id}")
    public ResponseEntity<Product> updateProduct(
            @PathVariable("id") Long id,
            @ModelAttribute Product productDetails,
            @RequestParam(value = "file",
                    required = false) MultipartFile[] files) {
        return productRepository.findById(id).map(existingProduct -> {
            existingProduct.setTitle(productDetails.getTitle());
            existingProduct.setDescription(productDetails.getDescription());
            existingProduct.setPrice(productDetails.getPrice());

            if (files != null && files.length > 0 && !files[0].isEmpty()) {
                List<String> newCloudImageUrls = imageService.uploadProductImage(files, existingProduct.getId());
                List<String> currentUrls = existingProduct.getImageUrls() != null
                        ? new ArrayList<>(existingProduct.getImageUrls())
                        : new ArrayList<>();

                currentUrls.addAll(newCloudImageUrls);

                existingProduct.setImageUrls(currentUrls);
            }

            Product updateProduct = productRepository.save(existingProduct);
            return ResponseEntity.ok(updateProduct);
        }).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/products/{id}/images")
    public ResponseEntity<Product> deleteImg(
            @PathVariable("id") Long id,
            @RequestParam("imgUrl") String imageUrl
    ) {
        return productRepository.findById(id).map(product -> {
            List<String> currentUrls = product.getImageUrls();

            if (currentUrls != null && currentUrls.contains(imageUrl)) {
                imageService.deleteProductImageFromCloud(imageUrl);

                currentUrls.remove(imageUrl);
                product.setImageUrls(currentUrls);

                Product newUpdateProduct = productRepository.save(product);
                return ResponseEntity.ok(newUpdateProduct);
            }
            return ResponseEntity.badRequest().body(product);
        }).orElseGet(() -> ResponseEntity.notFound().build());
    }


}
