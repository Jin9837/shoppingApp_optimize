package com.example.shoppingapp_3.domain.dto;


import com.example.shoppingapp_3.domain.User;
import com.example.shoppingapp_3.domain.dto.OrderForBuyer2Dto;
import com.example.shoppingapp_3.domain.dto.OrderProductForBuyerDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserOrderDto {
    private User user;
    private OrderForBuyer2Dto orderForBuyerDto;
    private List<OrderProductForBuyerDto> orderProductForBuyerDto;
}
