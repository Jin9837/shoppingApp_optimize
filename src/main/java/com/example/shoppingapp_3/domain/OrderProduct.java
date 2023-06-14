package com.example.shoppingapp_3.domain;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "order_product")
public class OrderProduct {
    @EmbeddedId
    private OrderProductId id;

    @ManyToOne
    @MapsId("orderId")
    @JoinColumn(name = "order_id", referencedColumnName = "order_id")
    private Order order;

    @ManyToOne
    @MapsId("productId")
    @JoinColumn(name = "product_id", referencedColumnName = "product_id")
    private Product product;

    @Column(name = "purchased_quantity")
    private int purchasedQuantity;
    @Column(name = "execution_retail_price")
    private double executionRetailPrice;
    @Column(name = "execution_wholesale_price")
    private double executionWholesalePrice;
}

