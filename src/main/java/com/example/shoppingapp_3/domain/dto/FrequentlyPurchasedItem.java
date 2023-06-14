package com.example.shoppingapp_3.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class FrequentlyPurchasedItem {
    Long productId;
    String name;
    Long purchasedQuantity;
}
