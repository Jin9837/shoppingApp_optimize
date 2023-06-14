package com.example.shoppingapp_3.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderForBuyer2Dto {
    private Long orderId;
    private String orderStatus;
    private Date datePlaced;
    private Double totalPrice;
}
