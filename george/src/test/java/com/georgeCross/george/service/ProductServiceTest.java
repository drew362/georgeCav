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
        String title = "Product 1";
        List<Product> products = new ArrayList<>();
        products.add(new Product(title, "Petia", "Description 1"));
        products.add(new Product("Product 2", "Micola", "Description 2"));
        products.add(new Product(null, "Ivan", "Description 3"));

        when(productRepository.findByNumber(title)).thenReturn(products);

        // Act
        List<Product> result = productService.getListFindByNumber(title);

        // Assert
        Assertions.assertEquals(3, result.size());
        Assertions.assertEquals(products.get(0).getNumber(), result.get(0).getNumber());
    }

    @Test
    public void testListProductsWithTitle() {
        // Arrange
        String title = "Product 1";
        List<Product> products = new ArrayList<>();
        products.add(new Product(title, null, null));

        when(productRepository.findByNumber(title)).thenReturn(products);

        // Act
        List<Product> result = productService.getListFindByNumber(title);

        // Assert
        Assertions.assertEquals(1, result.size());
        Assertions.assertEquals(products.get(0).getNumber(), result.get(0).getNumber());
    }
}