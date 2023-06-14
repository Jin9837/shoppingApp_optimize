package com.example.shoppingapp_3.service.Async;

import com.example.shoppingapp_3.domain.User;
import com.example.shoppingapp_3.domain.dto.OrderForBuyer2Dto;
import com.example.shoppingapp_3.domain.dto.OrderProductForBuyerDto;
import com.example.shoppingapp_3.domain.dto.UserOrderDto;
import com.example.shoppingapp_3.exception.CustomizedException;
import com.example.shoppingapp_3.service.OrderService;
import com.example.shoppingapp_3.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class NormalServiceImp {
    private final OrderService orderService;
    private final UserService userService;
    public UserOrderDto getUserOrder(Long userId, Long orderId) throws CustomizedException {
        User user = userService.getUserByUserId(userId);
        OrderForBuyer2Dto orderForBuyer2Dto = orderService.getOrderDto(orderId);
        List<OrderProductForBuyerDto> orderProducts = orderService.getAllProductByUserIdAndOrderId(userId, orderId);
        return new UserOrderDto(user, orderForBuyer2Dto, orderProducts);
    }


}
