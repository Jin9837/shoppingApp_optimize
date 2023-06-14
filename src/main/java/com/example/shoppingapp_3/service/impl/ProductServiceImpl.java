package com.example.shoppingapp_3.service.impl;

import com.example.shoppingapp_3.dao.ProductDao;
import com.example.shoppingapp_3.domain.Product;
import com.example.shoppingapp_3.domain.User;
import com.example.shoppingapp_3.domain.dto.*;
import com.example.shoppingapp_3.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    final private ProductDao productDao;
    @Override
    @Cacheable(value = "products")
    public List<ProductDto> listForBuyer() {
        // Simulate a slow method with Thread.sleep to test the @Cacheable,
        // the first time we need 5s to execute
        // the second time we execute, the result should be store in cache,
        // and the result is being returned from the cache instead of running the method.
        try {
            Thread.sleep(5000); // pause for 5000 milliseconds (5 seconds)
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return productDao.findAllProductsByBuyer();
    }

    @Override
    public ProductDetailForBuyerDto detailForBuyer(Long pid) {
        return productDao.findProductDetailByBuyer(pid);
    }

    @Override
    public List<ProductDto> frequentlyPurchasedItems(Long userId) {
        return productDao.findTop3FrequentlyPurchasedItems(userId);
    }

    @Override
    public List<ProductDto> recentlyPurchasedItems(Long userId) {
        return productDao.findTop3RecentlyPurchasedItems(userId);
    }

    @Override
    public List<ProductDto> listForSeller() {
        return productDao.findAllProductsBySeller();
    }

    @Override
    public ProductDetailForSellerDto detailForSeller(Long productId) {
        return productDao.findProductDetailsBySeller(productId);
    }

    @Override
    public ProductWithIgnoredFields addProduct(ProductWithIgnoredFields product) {
        return productDao.addProduct(product);
    }

    @Override
    public ProductWithIgnoredFields updateProduct(ProductWithIgnoredFields product) {
        return null;
    }

    @Override
    public void update(Product product) {
        productDao.updateProduct(product);
    }

    @Override
    public ProductDto profitableProduct() {
        return productDao.profitableProduct();
    }

    @Override
    public List<ProductDto> popularProducts() {
        return productDao.popularProducts();
    }

    @Override
    public List<ProductDto> soldMostProducts() {
        return productDao.soldMostProductions();
    }

    @Override
    public List<User> spentMostBuyers() {
        return productDao.spentMostBuyers();
    }

    @Override
    public ProductQuantity getProductQuantity(Long productId) {
        return productDao.getQuantity(productId);
    }

    @Override
    public void delProduct(Long productId) {
        productDao.delProductById(productId);
    }
}
