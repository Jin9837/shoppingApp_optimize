package com.example.shoppingapp_3.service;

import com.example.shoppingapp_3.domain.Item;
import com.example.shoppingapp_3.domain.Order;
import com.example.shoppingapp_3.domain.dto.*;
import com.example.shoppingapp_3.exception.CustomizedException;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public interface OrderService {
    List<OrderForBuyerDto> getAllOrdersByUserId(Long userId);
    public List<OrderProductForBuyerDto> getAllProductByUserIdAndOrderId(Long userId, Long orderId) throws CustomizedException;
    //void generateOrder(Long productId, Integer quantity) throws CustomizedException;

    List<OrderProductForSellerDto> getAllProductByOrderIdForAdmin(Long orderId);

    void generateOrder(List<Item> items) throws CustomizedException;

    void cancelOrder(Long orderId);

    void completeOrder(Long orderId);

    List<Order> getOrdersBySeller();

    OrderForBuyer2Dto getOrderDto(Long orderId);

    CompletableFuture<OrderForBuyer2Dto> getOrderDtoAsync(Long orderId);

    CompletableFuture<List<OrderProductForBuyerDto>> getAllProductByUserIdAndOrderIdAsync(Long userId, Long orderId) throws CustomizedException;
}
