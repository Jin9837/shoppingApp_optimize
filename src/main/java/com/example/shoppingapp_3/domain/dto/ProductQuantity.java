package com.example.shoppingapp_3.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductQuantity {
    Long productId;
    String name;
    Integer stockQuantity;
}
