package com.example.shoppingapp_3.service;

import com.example.shoppingapp_3.domain.Product;
import com.example.shoppingapp_3.domain.User;
import com.example.shoppingapp_3.domain.dto.*;

import javax.transaction.Transactional;
import java.util.List;

public interface ProductService {
    List<ProductDto> listForBuyer();

    ProductDetailForBuyerDto detailForBuyer(Long pid);

    @Transactional
    List<ProductDto> frequentlyPurchasedItems(Long userId);

    @Transactional
    List<ProductDto> recentlyPurchasedItems(Long userId);

    @Transactional
    List<ProductDto> listForSeller();

    @Transactional
    ProductDetailForSellerDto detailForSeller(Long productId);

    @Transactional
    ProductWithIgnoredFields addProduct(ProductWithIgnoredFields product);

    @Transactional
    ProductWithIgnoredFields updateProduct(ProductWithIgnoredFields product);

    @Transactional
    void update(Product product);

    @Transactional
    ProductDto profitableProduct();

    @Transactional
    List<ProductDto> popularProducts();

    @Transactional
    List<ProductDto> soldMostProducts();

    @Transactional
    List<User> spentMostBuyers();

    @Transactional
    ProductQuantity getProductQuantity(Long productId);

    @Transactional
    void delProduct(Long productId);
}
