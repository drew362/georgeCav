package com.georgeCross.george.repositories;

import com.georgeCross.george.models.Category;
import com.georgeCross.george.models.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    List<Product> findByCategory(Category category);

    Optional<Product> findBySlug(String slug);
}
