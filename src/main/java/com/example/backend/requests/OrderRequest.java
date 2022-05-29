package com.example.backend.requests;

import lombok.Data;

import javax.validation.constraints.NotBlank;


@Data
public class OrderRequest {
    @NotBlank
    private Long userId;

    private String comment;

    @NotBlank
    private String address;

    @NotBlank
    private String phoneNumber;
}
