package com.Nkosopa.NMarket.Repository.Product.impl;

import com.Nkosopa.NMarket.Entity.Product.Product;
import com.Nkosopa.NMarket.Repository.BaseRepositoryImpl;
import com.Nkosopa.NMarket.Repository.Product.ProductRepository;
import jakarta.persistence.EntityManager;

public class ProductRepositoryImpl extends BaseRepositoryImpl<Product, Long> implements ProductRepository {
    public ProductRepositoryImpl(EntityManager entityManager) {
        super(Product.class, entityManager);
    }
}
