package com.georgeCross.george.service;

import com.georgeCross.george.models.Product;
import com.georgeCross.george.repositories.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    public List<Product> getListFindByTitle(String title) {
        return productRepository.findByTitle(title);
    }
}
