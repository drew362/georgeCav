package com.georgeCross.george.repositories;

import com.georgeCross.george.models.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {

    List<Product> findByNumber(String number);

    List<Product> findByName(String name);
}
