package com.georgeCross.george.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "search1")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Product {

    @Id
    @Column(name = "id")
    private Long id;
    @Column(name = "number")
    private String number;
    @Column(name = "description", columnDefinition = "text")
    private String description;
    @Column(name = "name")
    private String name;

    public Product(String number, String name, String description) {
    }
}
