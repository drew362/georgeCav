package com.georgeCross.george.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.logging.log4j.message.AsynchronouslyFormattable;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@Entity
@Table(name = "product")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Product {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    @Column(name="id")
    private Long id;
    @Column(name= "title")
    private String title;
    @Column(name = "description",columnDefinition="text")
    private String description;
    @Column(name ="price")
    private String price;
    @Column(name = "city")
    private String city;
    @Column(name="author")
    private String author;
}
