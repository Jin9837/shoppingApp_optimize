package com.example.shoppingapp_3.controller;

import com.example.shoppingapp_3.common.ApiRestResponse;
import com.example.shoppingapp_3.service.WatchlistService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class WatchlistController {
    private final WatchlistService watchlistService;

    @PostMapping("/user/add_product")
    @PreAuthorize("hasAnyAuthority('buyer')")
    public ApiRestResponse addProductIntoWatchlist(@RequestParam("productId") Long productId) {
        watchlistService.insertProduct(productId);
        return ApiRestResponse.success();
    }

    @DeleteMapping("/user/delete_product")
    @PreAuthorize("hasAnyAuthority('buyer')")
    public ApiRestResponse deleteProductFromWatchlist(@RequestParam("productId") Long productId) {
        watchlistService.deleteProduct(productId);
        return ApiRestResponse.success();
    }

    @GetMapping("/user/view_products")
    @PreAuthorize("hasAnyAuthority('buyer')")
    public ApiRestResponse allProductInWatchlist() {
        return ApiRestResponse.success(watchlistService.productsInWatchlist());
    }
}
