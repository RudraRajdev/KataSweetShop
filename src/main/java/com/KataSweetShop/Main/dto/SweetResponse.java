package com.KataSweetShop.Main.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SweetResponse {
    private Long id;
    private String name;
    private String category;
    private Long price;
    private Integer quantity;
}
