package com.example.shoppingapp_3.dao;

import com.example.shoppingapp_3.domain.ProductWatchlist;
import com.example.shoppingapp_3.domain.dto.ProductDto;

import java.util.List;

public interface WatchlistDao {
    void insertIntoWatchlist(ProductWatchlist productWatchlist);

    void deleteFromWatchlist(Long productId, Long userId);

    List<ProductDto> getProductsFromWatchlistByUserId(Long userId);
}
