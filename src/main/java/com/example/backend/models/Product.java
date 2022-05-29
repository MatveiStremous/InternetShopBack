package com.example.backend.models;

import com.fasterxml.jackson.annotation.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "products")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="id")
    private Long id;

    @Column(name="title")
    private String title;

    @Column(name="price")
    private float price;

    @Column(name="imageUrl")
    private String imageUrl;

    @Column(name="dateOfCreation")
    private LocalDateTime dateOfCreation;

    @Column(name="description",columnDefinition = "text")
    private String description;

    @Column(name="size")
    private String size;

    @Column(name="materials")
    private String materials;

    @Column(name="quantity")
    private int quantity;

    @PrePersist
    private void init(){
        dateOfCreation=LocalDateTime.now();
    }

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "product")
    //@JsonBackReference
//    @JsonIdentityInfo(
//            generator = ObjectIdGenerators.PropertyGenerator.class,
//            property = "id")
    private List<Cart> carts = new ArrayList<>();
}
