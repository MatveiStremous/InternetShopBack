package com.example.backend.requests;

import lombok.Data;


import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
public class LoginRequest {

    @NotBlank
    @Email
    @Size(max=50)
    private String email;

    @NotBlank
    @Size(min=6)
    private String password;

}
