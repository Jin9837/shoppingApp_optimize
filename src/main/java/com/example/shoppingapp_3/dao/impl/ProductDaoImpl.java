package com.example.shoppingapp_3.dao.impl;

import com.example.shoppingapp_3.dao.ProductDao;
import com.example.shoppingapp_3.domain.Product;
import com.example.shoppingapp_3.domain.User;
import com.example.shoppingapp_3.domain.dto.*;
import lombok.RequiredArgsConstructor;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class ProductDaoImpl implements ProductDao {

    private final SessionFactory sessionFactory;

    @Override
    public List<ProductDto> findAllProductsByBuyer() {
        Session session = sessionFactory.getCurrentSession();
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<ProductDto> criteriaQuery = builder.createQuery(ProductDto.class);
        Root<Product> root = criteriaQuery.from(Product.class);
        criteriaQuery.select(builder.construct(ProductDto.class, root.get("productId"), root.get("name")));
        criteriaQuery.where(builder.greaterThan(root.get("stockQuantity"), 0));
        TypedQuery<ProductDto> query = session.createQuery(criteriaQuery);
        return query.getResultList();
    }

    @Override
    public ProductDetailForBuyerDto findProductDetailsByBuyer(Long productId) {
        return null;
    }

    @Override
    public List<ProductDto> findTop3FrequentlyPurchasedItems(Long userId) {
        Session session = sessionFactory.getCurrentSession();
        Query<ProductDto> query = session.createQuery(
                "SELECT new com.example.shoppingapp_3.domain.dto.ProductDto(op.product.productId, op.product.name) " +
                "FROM Order o " +
                "JOIN o.orderProducts op " +
                "WHERE o.user.userId = :userId AND o.orderStatus <> 'Canceled' " +
                "GROUP BY op.product.productId " +
                "ORDER BY COUNT(op.product.productId) DESC, op.product.productId ASC",
                ProductDto.class);
        query.setParameter("userId", userId);
        query.setMaxResults(3);
        return query.list();
    }

    @Override
    public List<ProductDto> findTop3RecentlyPurchasedItems(Long userId) {
        Session session = sessionFactory.getCurrentSession();
        Query<ProductDto> query = session.createQuery(
                        "SELECT new com.example.shoppingapp_3.domain.dto.ProductDto(op.product.productId, op.product.name) " +
                                "FROM OrderProduct op " +
                                "WHERE op.order.user.userId = :userId " +
                                "AND op.order.orderStatus <> 'Canceled' " +
                                "GROUP BY op.product.productId " +
                                "ORDER BY max(op.order.datePlaced) DESC, op.product.productId ASC", ProductDto.class);
        query.setParameter("userId", userId);
        query.setMaxResults(3);
        return query.list();
    }

    @Override
    public List<ProductDto> findAllProductsBySeller() {
        Session session = sessionFactory.getCurrentSession();
        Query<ProductDto> query = session.createQuery("SELECT new com.example.shoppingapp_3.domain.dto.ProductDto(p.productId, p.name) " +
                "FROM Product p", ProductDto.class);
        return query.list();
    }

    @Override
    public ProductDetailForSellerDto findProductDetailsBySeller(Long productId) {
        Session session = sessionFactory.getCurrentSession();
        Query<ProductDetailForSellerDto> query = session.createQuery("SELECT new com.example.shoppingapp_3.domain.dto.ProductDetailForSellerDto(p.description, p.retailPrice, p.wholesalePrice, p.stockQuantity) " +
                "FROM Product p " +
                "WHERE p.productId = :productId", ProductDetailForSellerDto.class);
        query.setParameter("productId", productId);
        return query.uniqueResult();
    }

    @Override
    public ProductWithIgnoredFields updateProduct(ProductWithIgnoredFields product) {
        return null;
    }

    @Override
    public ProductQuantity getQuantity(Long productId) {
        Session session = sessionFactory.getCurrentSession();
        Query<ProductQuantity> query = session.createQuery("SELECT new com.example.shoppingapp_3.domain.dto.ProductQuantity(p.productId, p.name, p.stockQuantity) " +
                "FROM Product p " +
                "WHERE p.productId = :productId", ProductQuantity.class);
        query.setParameter("productId", productId);
        return query.uniqueResult();
    }

    @Override
    public ProductWithIgnoredFields addProduct(ProductWithIgnoredFields product) {
        Session session = sessionFactory.getCurrentSession();
        session.save(product);
        return product;
    }

    @Override
    public ProductDto profitableProduct() {
        Session session = sessionFactory.getCurrentSession();
        // 1. get the most profitable product from product table
        String productQuery = "SELECT p.productId, (p.retailPrice - p.wholesalePrice) as profit " +
                "From Product p " +
                "ORDER BY profit DESC";
        Object[] r1 = session.createQuery(productQuery, Object[].class).setMaxResults(1).list().get(0);

        // 2. get the most profitable product from orderProduct table
        String orderProductQuery = "SELECT op.product.productId, (op.executionRetailPrice - op.executionWholesalePrice) as profit " +
                "From OrderProduct op " +
                "WHERE op.order.orderStatus <> 'Canceled' " +
                "ORDER BY profit DESC";
        Object[] r2 = session.createQuery(orderProductQuery, Object[].class).setMaxResults(1).list().get(0);

        // 3. compare the profits
        Long pId1 = (Long) r1[0];
        Long pId2 = (Long) r2[0];
        Double profit1 = (Double) r1[1];
        Double profit2 = (Double) r2[1];
        Long pId = pId1;
        pId = profit1 >= profit2 ? pId1 : pId2;

        Query<ProductDto> query = session.createQuery("" +
                "SELECT p.productId, (p.retailPrice - p.wholesalePrice) as profit " +
                "FROM Product p " +
                "WHERE p.productId = :productId", ProductDto.class);
        query.setParameter("productId", pId);
        return query.uniqueResult();
    }

    @Override
    public List<ProductDto> popularProducts() {
        Session session = sessionFactory.getCurrentSession();
        // get the top 3 popular product Id
        String hql = "SELECT new com.example.shoppingapp_3.domain.dto.ProductDto(op.product.productId, op.product.name) " +
                "FROM OrderProduct op " +
                "WHERE op.order.orderStatus = 'Completed' " +
                "GROUP BY op.product.productId " +
                "ORDER BY COUNT(op.product.productId) DESC, op.product.productId ASC";
        Query<ProductDto> query = session.createQuery(hql, ProductDto.class);
        query.setMaxResults(3);

        return query.list();
    }

    @Override
    public List<ProductDto> soldMostProductions() {
        Session session = sessionFactory.getCurrentSession();
        String hql = "SELECT new com.example.shoppingapp_3.domain.dto.ProductDto(op.product.productId, op.product.name) " +
                "FROM OrderProduct op " +
                "WHERE op.order.orderStatus <> 'Canceled' " +
                "GROUP BY op.product.productId " +
                "ORDER BY COUNT(op.purchasedQuantity) DESC, op.product.productId ASC";
        Query<ProductDto> query = session.createQuery(hql, ProductDto.class);
        query.setMaxResults(3);

        return query.list();
    }

    @Override
    public List<User> spentMostBuyers() {
        Session session = sessionFactory.getCurrentSession();
        String hql = "SELECT o.user, SUM(op.purchasedQuantity * op.executionRetailPrice) as totalProfit " +
                "FROM Order o JOIN o.orderProducts op " +
                "WHERE op.order.orderStatus = 'Completed' " +
                "GROUP BY o.user " +
                "ORDER BY totalProfit DESC, o.user.username ASC";
        Query query = session.createQuery(hql);
        query.setMaxResults(3);
        List<Object[]> resultList = query.list();
        List<User> top3Buyers = new ArrayList<>();
        for (Object[] result : resultList)
        {
            top3Buyers.add((User) result[0]);
        }

        return top3Buyers;

    }

    @Override
    public ProductDetailForBuyerDto findProductDetailByBuyer(Long productId) {
        Session session = sessionFactory.getCurrentSession();
        Query<ProductDetailForBuyerDto> query = session.createQuery("SELECT new com.example.shoppingapp_3.domain.dto.ProductDetailForBuyerDto(p.description, p.retailPrice) FROM Product p WHERE p.productId = :productId", ProductDetailForBuyerDto.class);
        query.setParameter("productId", productId);
        return query.uniqueResult();
    }

    @Override
    public Product getProductByProductId(Long productId) {
        Session session = sessionFactory.getCurrentSession();
        Query<Product> query = session.createQuery("FROM Product WHERE productId = :productId", Product.class);
        query.setParameter("productId", productId);
        return query.uniqueResult();
    }

    @Override
    public void updateProduct(Product product) {
        Session session = sessionFactory.getCurrentSession();
        session.merge(product);
    }

    @Override
    public void delProductById(Long productId) {
        Session session = sessionFactory.getCurrentSession();
        // For here, cannot just use DELETE FROM Product WHERE productId = :productId to delete
        // since it will directly delete the record from the database,
        // bypassing the Hibernate context.
        // Thus, Hibernate doesn't get a chance to cascade the deletion to
        // the related OrderProduct and ProductWatchlist entities,
        // which leads to a foreign key constraint violation if there are
        // still related entities in those tables.
        // Thus, we need to fetch the Product entity first,
        // and then delete it using the Session.delete() method
        Product product = session.get(Product.class, productId);
        if (product != null) {
            session.delete(product);
        }
    }

}
