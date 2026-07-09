package com.georgeCross.george.service;

import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;

import com.georgeCross.george.models.Georg;

import com.georgeCross.george.repositories.GeorgRepository;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class GeorgServiceTest {

    @Mock
    private GeorgRepository georgRepository;

    @InjectMocks
    private GeorgService georgService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testListProducts() {
        // Arrange
        String name = "Product 1";
        List<Georg> products = new ArrayList<>();
        products.add(new Georg(null, 34525, "Petia", name));
        products.add(new Georg(null,2, "Micola", "Description 2"));
        products.add(new Georg(null,5757575, "Ivan", "Description 3"));

        when(georgRepository.findByNumberOrName(name)).thenReturn(products);

        // Act
        List<Georg> result = georgService.getListFindByNumberOrName(name);

        // Assert
        Assertions.assertEquals(3, result.size());
        Assertions.assertEquals(products.get(0).getNumber(), result.get(0).getNumber());
    }

    @Test
    public void testListProductsWithTitle() {
        // Arrange
        String name = "Product 1";
        List<Georg> products = new ArrayList<>();
        products.add(new Georg(null, 454554, null,name));

        when(georgRepository.findByNumberOrName(name)).thenReturn(products);

        // Act
        List<Georg> result = georgService.getListFindByNumberOrName(name);

        // Assert
        Assertions.assertEquals(1, result.size());
        Assertions.assertEquals(products.get(0).getNumber(), result.get(0).getNumber());
    }
}