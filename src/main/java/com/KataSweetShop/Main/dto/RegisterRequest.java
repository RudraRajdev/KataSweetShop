package com.KataSweetShop.Main.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class RegisterRequest {
    @NotBlank
    private String username;
    private String password;
    private String role="ROLE_USER";
}
