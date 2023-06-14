package com.example.shoppingapp_3.dao.impl;

import com.example.shoppingapp_3.dao.OrderDao;
import com.example.shoppingapp_3.domain.Order;
import com.example.shoppingapp_3.domain.OrderProduct;
import com.example.shoppingapp_3.domain.dto.OrderForBuyer2Dto;
import com.example.shoppingapp_3.domain.dto.OrderForBuyerDto;
import com.example.shoppingapp_3.domain.dto.OrderProductForBuyerDto;
import com.example.shoppingapp_3.domain.dto.OrderProductForSellerDto;
import lombok.RequiredArgsConstructor;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class OrderDaoImpl implements OrderDao {

    private final SessionFactory sessionFactory;
    @Override
    public List<OrderForBuyerDto> getAllOrdersByUserId(Long userId) {
        Session session = sessionFactory.getCurrentSession();
        Query<OrderForBuyerDto> query = session.createQuery("SELECT new com.example.shoppingapp_3.domain.dto.OrderForBuyerDto(o.orderId, o.orderStatus, o.datePlaced) " +
                "FROM Order o " +
                "WHERE o.user.userId =:userId", OrderForBuyerDto.class);

        query.setParameter("userId", userId);

        return query.list();
    }

    @Override
    public List<OrderProductForBuyerDto> getOrderProductByUserIdAndOrderId(Long userId, Long orderId) {
        Session session = sessionFactory.getCurrentSession();
        Query<OrderProductForBuyerDto> query = session.createQuery("SELECT new com.example.shoppingapp_3.domain.dto.OrderProductForBuyerDto(op.product.name, op.purchasedQuantity, op.executionRetailPrice, op.product.description) " +
                "FROM Order o JOIN o.orderProducts op " +
                "WHERE o.user.userId = :userId AND o.orderId = :orderId", OrderProductForBuyerDto.class);

        query.setParameter("userId", userId);
        query.setParameter("orderId", orderId);

        return query.list();

    }

    @Override
    public List<OrderProductForSellerDto> getOrderProductByOrderIdForAdmin(Long orderId) {
        Session session = sessionFactory.getCurrentSession();
        Query<OrderProductForSellerDto> query = session.createQuery("" +
                "SELECT new com.example.shoppingapp_3.domain.dto.OrderProductForSellerDto(op.product.name, op.purchasedQuantity, op.executionRetailPrice, op.executionWholesalePrice,op.product.description) " +
                "FROM OrderProduct op " +
                "WHERE op.order.orderId = :orderId", OrderProductForSellerDto.class);
        query.setParameter("orderId", orderId);
        return query.list();
    }

    @Override
    public List<Order> getOrders() {
        Session session = sessionFactory.getCurrentSession();
        Query<Order> query = session.createQuery("FROM Order", Order.class);
        return query.list();
    }

    @Override
    public Long generateOrder(Order order) {
        Session session = sessionFactory.getCurrentSession();
        session.save(order);
        return order.getOrderId();
    }

    @Override
    public void generateOrderProduct(OrderProduct orderProduct) {
        Session session = sessionFactory.getCurrentSession();
        session.save(orderProduct);
    }

    @Override
    public void updateOrder(Order order) {
        Session session = sessionFactory.getCurrentSession();
        session.saveOrUpdate(order);
    }

    @Override
    public void completeOrder(Long orderId) {

    }

    @Override
    public void cancelOrder(Long orderId) {

    }

    @Override
    public Order getOrderByOrderId(Long orderId) {
        Session session = sessionFactory.getCurrentSession();
        Order order = session.get(Order.class, orderId);
        return order;
    }

    @Override
    public void insertOrderProduct(OrderProduct orderProduct) {

    }

    @Override
    public void updateOrderProduct(OrderProduct orderProduct) {

    }

    @Override
    public OrderForBuyer2Dto getOrderForBuyer2(Long orderId) {
        return null;
    }
}
