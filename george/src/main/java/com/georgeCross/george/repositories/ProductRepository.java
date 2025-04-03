package com.georgeCross.george.repositories;

import com.georgeCross.george.models.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {

    @Query("SELECT p FROM Product p WHERE p.number LIKE %?1% OR p.name LIKE %?1%")
    List<Product> findByNumberOrName(String query);
}
