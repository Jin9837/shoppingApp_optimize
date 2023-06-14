package com.example.shoppingapp_3.service.impl;

import com.example.shoppingapp_3.dao.OrderDao;
import com.example.shoppingapp_3.dao.ProductDao;
import com.example.shoppingapp_3.dao.UserDao;
import com.example.shoppingapp_3.domain.*;
import com.example.shoppingapp_3.domain.dto.OrderForBuyer2Dto;
import com.example.shoppingapp_3.domain.dto.OrderForBuyerDto;
import com.example.shoppingapp_3.domain.dto.OrderProductForBuyerDto;
import com.example.shoppingapp_3.domain.dto.OrderProductForSellerDto;
import com.example.shoppingapp_3.exception.CustomizedException;
import com.example.shoppingapp_3.exception.CustomizedExceptionEnum;
import com.example.shoppingapp_3.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.apache.tomcat.jni.Time;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;

@Service
@Transactional
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderDao orderDao;
    private final ProductDao productDao;
    private final UserDao userDao;

    @Override
    public List<OrderForBuyerDto> getAllOrdersByUserId(Long userId) {
        return orderDao.getAllOrdersByUserId(userId);
    }

    @Override
    public List<OrderProductForBuyerDto> getAllProductByUserIdAndOrderId(Long userId, Long orderId) throws CustomizedException {
        Order order = orderDao.getOrderByOrderId(orderId);
        if (!order.getUser().getUserId().equals(userId)) throw new CustomizedException(CustomizedExceptionEnum.ACCESS_IS_DENIED);
        return orderDao.getOrderProductByUserIdAndOrderId(userId, orderId);
    }

    @Override
    public List<OrderProductForSellerDto> getAllProductByOrderIdForAdmin(Long orderId) {
        return orderDao.getOrderProductByOrderIdForAdmin(orderId);
    }

    @Override
    public void generateOrder(List<Item> items) throws CustomizedException {
        // check the stock to see we have enough item or not
        for (Item item : items) {
            Long qid = item.getProductId();
            Integer quantity = item.getQuantity();
            Product product = productDao.getProductByProductId(qid);

            // if the product was deleted, throw exception
            if (product==null) throw new CustomizedException(CustomizedExceptionEnum.NOT_ENOUGH_INVENTORY);
            if (product.getStockQuantity() < quantity) throw new CustomizedException(CustomizedExceptionEnum.NOT_ENOUGH_INVENTORY);
        }

        // create a new order
        Long userId = Long.valueOf(SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString());
        Order order = new Order();
        order.setOrderStatus("Processing");
        order.setDatePlaced(new Timestamp(System.currentTimeMillis()));
        order.setUser(userDao.getUserByUserId(userId));
        Long orderId = orderDao.generateOrder(order);

        // create a list of OrderProduct
        List<OrderProduct> list = new ArrayList<>();
        for (Item item : items) {
            Long qid = item.getProductId();
            Integer quantity = item.getQuantity();
            Product product = productDao.getProductByProductId(qid);
            product.setStockQuantity(product.getStockQuantity() - quantity);
            productDao.updateProduct(product);

            //linked all related products to the order
            OrderProduct orderProduct = new OrderProduct();
            OrderProductId id = new OrderProductId();
            id.setProductId(product.getProductId());
            id.setOrderId(orderId);
            orderProduct.setId(id);
            orderProduct.setOrder(orderDao.getOrderByOrderId(orderId));
            orderProduct.setProduct(product);
            orderProduct.setExecutionRetailPrice(product.getRetailPrice());
            orderProduct.setExecutionWholesalePrice(product.getWholesalePrice());
            orderProduct.setPurchasedQuantity(quantity);
            orderDao.generateOrderProduct(orderProduct);
            list.add(orderProduct);
        }
        order.setOrderProducts(list);
        orderDao.updateOrder(order);
    }

    @Override
    public void cancelOrder(Long orderId) {
        Order order = orderDao.getOrderByOrderId(orderId);
        if (Objects.equals(order.getOrderStatus(), "Completed"))
        {
            throw new RuntimeException("Cannot change completed order");
        }

        List<OrderProduct> list = order.getOrderProducts();
        order.setOrderStatus("Canceled");
        // change each product stock back
        for (OrderProduct orderProduct : list)
        {
            Product product = orderProduct.getProduct();
            product.setStockQuantity(orderProduct.getPurchasedQuantity() + product.getStockQuantity());
            productDao.updateProduct(product);
            orderProduct.setProduct(product);
            orderDao.updateOrderProduct(orderProduct);
        }

        orderDao.updateOrder(order);
    }

    @Override
    public void completeOrder(Long orderId) {
        Order order = orderDao.getOrderByOrderId(orderId);
        order.setOrderStatus("Completed");
        orderDao.updateOrder(order);
    }

    @Override
    public List<Order> getOrdersBySeller() {
        return orderDao.getOrders();
    }

    @Override
    public OrderForBuyer2Dto getOrderDto(Long orderId) {
        return null;
    }

    @Override
    @Async("taskExecutor")
    public CompletableFuture<OrderForBuyer2Dto> getOrderDtoAsync(Long orderId) {
        OrderForBuyer2Dto orderForBuyer2Dto = orderDao.getOrderForBuyer2(orderId);
        return CompletableFuture.completedFuture(orderForBuyer2Dto);
    }

    @Override
    @Async("taskExecutor")
    public CompletableFuture<List<OrderProductForBuyerDto>> getAllProductByUserIdAndOrderIdAsync(Long userId, Long orderId) throws CustomizedException{
        Order order = orderDao.getOrderByOrderId(orderId);
        if (!order.getUser().getUserId().equals(userId)) throw new CustomizedException(CustomizedExceptionEnum.ACCESS_IS_DENIED);
        List<OrderProductForBuyerDto> list = orderDao.getOrderProductByUserIdAndOrderId(userId, orderId);
        return CompletableFuture.completedFuture(list);
    }
}
