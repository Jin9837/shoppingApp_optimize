package com.example.shoppingapp_3.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class ProductDetailForBuyerDto {
    private String description;
    private Double retailPrice;
}
