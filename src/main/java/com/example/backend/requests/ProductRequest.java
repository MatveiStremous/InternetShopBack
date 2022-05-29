package com.example.backend.requests;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class ProductRequest {
    @NotBlank
    private Long productId;

    @NotBlank
    private String title;

    @NotBlank
    private float price;

    @NotBlank
    private String imageUrl;

    private int quantity;

}
