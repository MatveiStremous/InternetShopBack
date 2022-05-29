package com.example.backend.requests;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class CartRequest {
    @NotBlank
    public Long userId;

    @NotBlank
    public Long productId;

    @NotBlank
    public int quantity;
}
