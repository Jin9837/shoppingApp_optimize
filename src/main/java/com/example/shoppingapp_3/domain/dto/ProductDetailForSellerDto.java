package com.example.shoppingapp_3.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ProductDetailForSellerDto {
    private String description;
    private Double retailPrice;
    private Double wholesalePrice;
    private Integer stockQuantity;
}
