package com.example.backend.models;

import com.fasterxml.jackson.annotation.*;
import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name="cart")
@Data
public class Cart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.EAGER)

    private User user;

    @ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.EAGER)

    private Product product;

    @Column(name="quantity")
    private int quantity;
}
