package com.example.shoppingapp_3.domain.dto;

import com.example.shoppingapp_3.domain.OrderProduct;
import com.example.shoppingapp_3.domain.ProductWatchlist;
import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
@Table(name = "Product")
public class ProductWithIgnoredFields {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_id")
    @JsonIgnore
    private Long productId;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "retail_price")
    private Double retailPrice;

    @Column(name = "wholesale_price")
    private Double wholesalePrice;

    @Column(name = "stock_quantity")
    private Integer stockQuantity;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonIgnore
    private List<OrderProduct> orderProducts;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonIgnore
    private List<ProductWatchlist> productWatchlists;
}
