package com.example.backend.requests;

import com.example.backend.models.enums.Role;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Data
public class UserRequest {
    @NotBlank
    private Long id;

    @NotBlank
    @Email
    private String email;

    @NotBlank
    private String name;

    @NotBlank
    private Role role;

    @NotBlank
    private boolean active;

}
