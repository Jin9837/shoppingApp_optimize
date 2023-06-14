package com.example.shoppingapp_3.dao.impl;

import com.example.shoppingapp_3.dao.WatchlistDao;
import com.example.shoppingapp_3.domain.ProductWatchlist;
import com.example.shoppingapp_3.domain.dto.ProductDto;
import lombok.RequiredArgsConstructor;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class WatchlistDaoImpl implements WatchlistDao {

    private final SessionFactory sessionFactory;

    @Override
    public void insertIntoWatchlist(ProductWatchlist productWatchlist) {
        Session session = sessionFactory.getCurrentSession();
        session.saveOrUpdate(productWatchlist);
    }

    @Override
    public void deleteFromWatchlist(Long productId, Long userId) {
        Session session = sessionFactory.getCurrentSession();
        String hql = "DELETE FROM ProductWatchlist pw where pw.id.productId = :productId and pw.id.userId = :userId";
        Query query = session.createQuery(hql);
        query.setParameter("productId", productId);
        query.setParameter("userId", userId);
        int result = query.executeUpdate();
    }

    @Override
    public List<ProductDto> getProductsFromWatchlistByUserId(Long userId) {
        Session session = sessionFactory.getCurrentSession();
        String hql = "SELECT new com.example.shoppingapp_3.domain.dto.ProductDto(pw.product.productId, pw.product.name) " +
                "FROM ProductWatchlist pw " +
                "where pw.id.userId = :userId AND pw.product.stockQuantity > 0";
        Query<ProductDto> query = session.createQuery(hql, ProductDto.class);
        query.setParameter("userId", userId);
        return query.list();
    }
}
