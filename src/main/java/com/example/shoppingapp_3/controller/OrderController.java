package com.example.shoppingapp_3.controller;

import com.example.shoppingapp_3.common.ApiRestResponse;
import com.example.shoppingapp_3.domain.Item;
import com.example.shoppingapp_3.exception.CustomizedException;
import com.example.shoppingapp_3.service.OrderService;
import com.example.shoppingapp_3.service.Async.AsyncServiceImp;
import com.example.shoppingapp_3.service.Async.NormalServiceImp;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class OrderController {
    private final OrderService orderService;
    private final NormalServiceImp normalServiceImp;
    private final AsyncServiceImp asyncServiceImp;



    @PostMapping("/user/purchase")
    @PreAuthorize("hasAnyAuthority('buyer')")
    public ApiRestResponse purchase(@RequestBody List<Item> items) throws CustomizedException {
        orderService.generateOrder(items);
        return ApiRestResponse.success();
    }

    @PostMapping("/user/complete_order")
    @PreAuthorize("hasAnyAuthority('buyer')")
    public ApiRestResponse completeOrderByUser(@RequestParam("orderId") Long orderId) {
        orderService.completeOrder(orderId);
        return ApiRestResponse.success();
    }

    @PostMapping("/user/cancel_order")
    @PreAuthorize("hasAnyAuthority('buyer')")
    public ApiRestResponse cancelOrderByUser(@RequestParam("orderId") Long orderId) {
        orderService.cancelOrder(orderId);
        return ApiRestResponse.success();
    }

    @GetMapping("/user/view_order_detail")
    @PreAuthorize("hasAnyAuthority('buyer')")
    public ApiRestResponse viewOrderDetail(@RequestParam("orderId") Long orderId) throws CustomizedException{
        Long userId = Long.valueOf(SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString());
        return ApiRestResponse.success(orderService.getAllProductByUserIdAndOrderId(userId, orderId));
    }


    // get order detail
    @GetMapping("/user/orders")
    @PreAuthorize("hasAnyAuthority('buyer')")
    public ApiRestResponse buyerOrders() {
        Long userId = Long.valueOf(SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString());
        return ApiRestResponse.success(orderService.getAllOrdersByUserId(userId));
    }

    @GetMapping("/user/{userId}/order/{orderId}")
    public ApiRestResponse UserOrderSync(@PathVariable("userId") Long userId, @PathVariable("orderId") Long orderId) throws CustomizedException {
        return ApiRestResponse.success(normalServiceImp.getUserOrder(userId, orderId));
    }

    @GetMapping("/async/user/{userId}/order/{orderId}")
    public ApiRestResponse UserOrderAsync(@PathVariable("userId") Long userId, @PathVariable("orderId") Long orderId) throws CustomizedException {
        return ApiRestResponse.success(asyncServiceImp.getUserOrderAsync(userId, orderId));
    }


    // admin
    @GetMapping("/admin/orders")
    @PreAuthorize("hasAnyAuthority('seller')")
    public ApiRestResponse sellerOrders() {
        return ApiRestResponse.success(orderService.getOrdersBySeller());
    }

    @GetMapping("/admin/order_details")
    @PreAuthorize("hasAnyAuthority('seller')")
    public ApiRestResponse viewOrderDetailForAdmin(@RequestParam("orderId") Long orderId) {
        return ApiRestResponse.success(orderService.getAllProductByOrderIdForAdmin(orderId));
    }

    @PostMapping("/admin/cancel_order")
    @PreAuthorize("hasAnyAuthority('seller')")
    public ApiRestResponse cancelOrderByAdmin(@RequestParam("orderId") Long orderId) {
        orderService.cancelOrder(orderId);
        return ApiRestResponse.success();
    }
    @PostMapping("/admin/complete_order")
    @PreAuthorize("hasAnyAuthority('seller')")
    public ApiRestResponse completeOrderByAdmin(@RequestParam("orderId") Long orderId) {
        orderService.completeOrder(orderId);
        return ApiRestResponse.success();
    }

}
