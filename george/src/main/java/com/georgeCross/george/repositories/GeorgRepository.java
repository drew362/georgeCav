package com.georgeCross.george.repositories;

import com.georgeCross.george.models.Georg;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface GeorgRepository extends JpaRepository<Georg, Long> {

    @Query("SELECT p FROM Georg p WHERE CAST(p.number AS string) = :query OR LOWER(p.name) LIKE LOWER(CONCAT('%', :query, '%'))")
    List<Georg> findByNumberOrName(@Param("query") String query);
}


