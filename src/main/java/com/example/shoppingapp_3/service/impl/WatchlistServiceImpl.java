package com.example.shoppingapp_3.service.impl;

import com.example.shoppingapp_3.dao.ProductDao;
import com.example.shoppingapp_3.dao.UserDao;
import com.example.shoppingapp_3.dao.WatchlistDao;
import com.example.shoppingapp_3.domain.Product;
import com.example.shoppingapp_3.domain.ProductWatchlist;
import com.example.shoppingapp_3.domain.ProductWatchlistId;
import com.example.shoppingapp_3.domain.User;
import com.example.shoppingapp_3.domain.dto.ProductDto;
import com.example.shoppingapp_3.service.WatchlistService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class WatchlistServiceImpl implements WatchlistService {

    private final WatchlistDao watchlistDao;
    private final ProductDao productDao;
    private final UserDao userDao;

    @Override
    public void insertProduct(Long productId) {
        Product product = productDao.getProductByProductId(productId);
        Long userId = Long.valueOf(SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString());
        User user = userDao.getUserByUserId(userId);
        ProductWatchlist productWatchlist = new ProductWatchlist();
        ProductWatchlistId id = new ProductWatchlistId();
        id.setProductId(productId);
        id.setUserId(userId);
        productWatchlist.setId(id);
        productWatchlist.setProduct(product);
        productWatchlist.setUser(user);
        watchlistDao.insertIntoWatchlist(productWatchlist);
    }

    @Override
    public void deleteProduct(Long productId) {
        Long userId = Long.valueOf(SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString());
        watchlistDao.deleteFromWatchlist(productId, userId);
    }

    @Override
    public List<ProductDto> productsInWatchlist() {
        Long userId = Long.valueOf(SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString());
        return watchlistDao.getProductsFromWatchlistByUserId(userId);
    }
}
