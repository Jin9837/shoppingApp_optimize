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
import java.util.concurrent.CompletableFuture;

@Service
@Transactional
@RequiredArgsConstructor
public class AsyncServiceImp {
    private final OrderService orderService;
    private final UserService userService;
    public UserOrderDto getUserOrderAsync(Long userId, Long orderId) throws CustomizedException {
        CompletableFuture<User> userFuture = userService.getUserByUserIdAsync(userId);
        CompletableFuture<OrderForBuyer2Dto> orderForBuyer2DtoFuture = orderService.getOrderDtoAsync(orderId);
        CompletableFuture<List<OrderProductForBuyerDto>> orderProductsFuture = orderService.getAllProductByUserIdAndOrderIdAsync(userId, orderId);
        CompletableFuture<UserOrderDto> userOrderDtoFuture = userFuture.allOf(
                userFuture,
                orderForBuyer2DtoFuture,
                orderProductsFuture
        ).thenApply(v -> {
            return new UserOrderDto(userFuture.join(), orderForBuyer2DtoFuture.join(), orderProductsFuture.join());
        });
        return userOrderDtoFuture.join();
    }
}
