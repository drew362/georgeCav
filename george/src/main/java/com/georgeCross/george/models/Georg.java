package com.georgeCross.george.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "georg")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Georg {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "number")
    private Integer number;

    @Column(name = "description", columnDefinition = "text")
    private String description;

    @Column(name = "fio", columnDefinition = "text")
    private String name;

}
