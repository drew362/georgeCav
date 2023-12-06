package com.georgeCross.george.service;

import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;

import com.georgeCross.george.models.Product;
import com.georgeCross.george.repositories.ProductRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductService productService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testListProducts() {
        // Arrange
        List<Product> products = new ArrayList<>();
        products.add(new Product("Product 1", "Test1", "Description 1"));
        products.add(new Product("Product 2", "Test2", "Description 2"));
        products.add(new Product("Product 3", "Test3", "Description 3"));

        when(productRepository.findAll()).thenReturn(products);

        // Act
        List<Product> result = productService.listProducts(null);

        // Assert
        Assertions.assertEquals(3, result.size());
        Assertions.assertEquals(products.get(0).getTitle(), result.get(0).getTitle());
        Assertions.assertEquals(products.get(2).getDescription(), result.get(2).getDescription());
    }

    @Test
    public void testListProductsWithTitle() {
        // Arrange
        String title = "Product 1";
        List<Product> products = new ArrayList<>();
        products.add(new Product(title, "10.0", "Description 1"));

        when(productRepository.findByTitle(title)).thenReturn(products);

        // Act
        List<Product> result = productService.listProducts(title);

        // Assert
        Assertions.assertEquals(1, result.size());
        Assertions.assertEquals(products.get(0).getTitle(), result.get(0).getTitle());
    }
}