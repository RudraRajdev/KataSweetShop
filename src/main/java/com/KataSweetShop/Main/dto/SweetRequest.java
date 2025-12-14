package com.KataSweetShop.Main.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class SweetRequest {
    @NotBlank private String name;
     private String category;
    @NotNull private Long price;
    @NotNull @Min
   (0) private Integer quantity;
}
