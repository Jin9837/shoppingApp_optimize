package com.example.shoppingapp_3.dao;

import com.example.shoppingapp_3.domain.Order;
import com.example.shoppingapp_3.domain.OrderProduct;
import com.example.shoppingapp_3.domain.dto.OrderForBuyer2Dto;
import com.example.shoppingapp_3.domain.dto.OrderForBuyerDto;
import com.example.shoppingapp_3.domain.dto.OrderProductForBuyerDto;
import com.example.shoppingapp_3.domain.dto.OrderProductForSellerDto;

import java.util.List;

public interface OrderDao {

    List<OrderForBuyerDto> getAllOrdersByUserId(Long userId);

    List<OrderProductForBuyerDto> getOrderProductByUserIdAndOrderId(Long userId, Long orderId);

    List<OrderProductForSellerDto> getOrderProductByOrderIdForAdmin(Long orderId);

    List<Order> getOrders();

    Long generateOrder(Order order);

    void generateOrderProduct(OrderProduct orderProduct);

    void updateOrder(Order order);

    // update the orderStatus from Processing to Completed
    void completeOrder(Long orderId);

    void cancelOrder(Long orderId);

    Order getOrderByOrderId(Long orderId);

    void insertOrderProduct(OrderProduct orderProduct);

    void updateOrderProduct(OrderProduct orderProduct);

    OrderForBuyer2Dto getOrderForBuyer2(Long orderId);
}
