package com.example.shoppingapp_3.domain;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "product_watchlist")
public class ProductWatchlist {
    @EmbeddedId
    private ProductWatchlistId id;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "user_id", insertable = false, updatable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "product_id", referencedColumnName = "product_id", insertable = false, updatable = false)
    private Product product;
}
