package com.example.shoppingapp_3.controller;

import com.example.shoppingapp_3.common.ApiRestResponse;
import com.example.shoppingapp_3.domain.Product;
import com.example.shoppingapp_3.domain.dto.ProductWithIgnoredFields;
import com.example.shoppingapp_3.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class ProductController {

    final private ProductService productService;

    @GetMapping("user/products")
    @PreAuthorize("hasAnyAuthority('buyer')")
    public ApiRestResponse getAllProductsByBuyer()
    {
        // add @Cacheable in the productService.listForBuyer() method
        return ApiRestResponse.success(productService.listForBuyer());
    }

    @GetMapping("/user/product_detail")
    @PreAuthorize("hasAnyAuthority('buyer')")
//    @ApiOperation(value = "Find product detail for user", response = Product.class)
    @Operation(summary = "Returns Hello World", security = @SecurityRequirement(name = "bearerAuth"))
    public ApiRestResponse getProductByBuyer (@RequestParam("pid") Long pid)
    {
        return ApiRestResponse.success(productService.detailForBuyer(pid));
    }

    // list top3 frequently products
    @GetMapping("/user/frequent_three")
    @PreAuthorize("hasAnyAuthority('buyer')")
    public ApiRestResponse getMostFrequentlyThree() {
        Long userId = Long.valueOf(SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString());
        return ApiRestResponse.success(productService.frequentlyPurchasedItems(userId));
    }

    @GetMapping("/user/recent_three")
    public ApiRestResponse getMostRecentThree() {
        Long userId = Long.valueOf(SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString());
        return ApiRestResponse.success(productService.recentlyPurchasedItems(userId));
    }


    // admin
    // get all products id and name
    @GetMapping("/admin/products")
    @PreAuthorize("hasAnyAuthority('seller')")
    public ApiRestResponse getAllProductsBySeller() {
        return ApiRestResponse.success(productService.listForSeller());
    }

    // get one product detail
    @GetMapping("/admin/product_detail")
    @PreAuthorize("hasAnyAuthority('seller')")
    public ApiRestResponse getProductBySeller (@RequestParam("productId") Long productId) {
        return ApiRestResponse.success(productService.detailForSeller(productId));
    }

    @PatchMapping("/admin/update_product")
    public ApiRestResponse updateProduct(@RequestBody Product product) {
        //return ApiRestResponse.success(productService.updateProduct(product));
        productService.update(product);
        return ApiRestResponse.success();
    }

    @PostMapping("/admin/add_product")
    @PreAuthorize("hasAnyAuthority('seller')")
    public ApiRestResponse addProduct(@RequestBody ProductWithIgnoredFields product) {
        return ApiRestResponse.success(productService.addProduct(product));
    }

    @DeleteMapping("/admin/del_product")
    @PreAuthorize("hasAnyAuthority('seller')")
    public ApiRestResponse delProduct(@RequestParam("productId") Long productId) {
        productService.delProduct(productId);
        return ApiRestResponse.success();
    }

    @GetMapping("/admin/product_quantity")
    @PreAuthorize("hasAnyAuthority('seller')")
    public ApiRestResponse getProductQuantity(@RequestParam("productId") Long productId) {
        return ApiRestResponse.success(productService.getProductQuantity(productId));
    }

    @GetMapping("/admin/most_profitable_product")
    @PreAuthorize("hasAnyAuthority('seller')")
    public ApiRestResponse getMostProfitableProduct() {
        return ApiRestResponse.success(productService.profitableProduct());
    }

    @GetMapping("/admin/most_popular_products")
    @PreAuthorize("hasAnyAuthority('seller')")
    public ApiRestResponse getMostPopularProduct() {
        return ApiRestResponse.success(productService.popularProducts());
    }

    @GetMapping("/admin/sold_most_products")
    @PreAuthorize("hasAnyAuthority('seller')")
    public ApiRestResponse getSoldMostProducts() {
        return ApiRestResponse.success(productService.soldMostProducts());
    }

    @GetMapping("/admin/spent_most_buyers")
    @PreAuthorize("hasAnyAuthority('seller')")
    public ApiRestResponse getMostSpentBuyers() {
        return ApiRestResponse.success(productService.spentMostBuyers());
    }
}
