package com.example.shoppingapp_3.service;

import com.example.shoppingapp_3.domain.dto.ProductDto;

import java.util.List;

public interface WatchlistService {
    void insertProduct(Long productId);

    void deleteProduct(Long productId);

    List<ProductDto> productsInWatchlist();
}
