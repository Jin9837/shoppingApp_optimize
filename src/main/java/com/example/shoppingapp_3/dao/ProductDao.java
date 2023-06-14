package com.example.shoppingapp_3.dao;

import com.example.shoppingapp_3.domain.Product;
import com.example.shoppingapp_3.domain.User;
import com.example.shoppingapp_3.domain.dto.*;

import java.util.List;

public interface ProductDao {
    // For buyer, all products(id and product_name) which are in stock
    List<ProductDto> findAllProductsByBuyer();

    // For buyer, the detail of a product (description and retail_price) which are in stock
    ProductDetailForBuyerDto findProductDetailsByBuyer(Long productId);

    // For buyer, The user should be able to view their top 3 most frequently purchased items.
    //(excluding canceled order, use item ID as tie breaker)
    List<ProductDto> findTop3FrequentlyPurchasedItems(Long userId);

    // For buyer The user can also view their top 3 most recently purchased items. (excluding
    //canceled order, use item id as tie breaker)
    List<ProductDto> findTop3RecentlyPurchasedItems(Long userId);

    // For buyer, all products(id and product_name)
    List<ProductDto> findAllProductsBySeller();

    // For seller, the detail of product
    ProductDetailForSellerDto findProductDetailsBySeller(Long productId);

    // For seller, update a product
    ProductWithIgnoredFields updateProduct(ProductWithIgnoredFields product);

    ProductQuantity getQuantity(Long productId);

    // For seller, add a product
    ProductWithIgnoredFields addProduct(ProductWithIgnoredFields product);

    // For seller, the seller can get a products which are most profit(from product table and orderProduct table)
    ProductDto profitableProduct();

    // The seller can see which 3 products are the most popular/sold (excluding
    //canceled and processing order).
    List<ProductDto> popularProducts();

    // The seller can also see the amount of total items sold successfully (excluding
    //canceled and ongoing order).
    List<ProductDto> soldMostProductions();

    //Show the top 3 buyers who spent the most (excluding canceled and ongoing
    //order, use first name as a tie breaker).
    List<User> spentMostBuyers();

    ProductDetailForBuyerDto findProductDetailByBuyer(Long productId);

    Product getProductByProductId(Long productId);

    void updateProduct(Product product);

    void delProductById(Long productId);
}
