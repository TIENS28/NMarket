package com.Nkosopa.NMarket.Repository.Product.impl;

import com.Nkosopa.NMarket.Entity.Product.ProductLongValue;
import com.Nkosopa.NMarket.Repository.BaseRepositoryImpl;
import com.Nkosopa.NMarket.Repository.Product.ProductLongValueRepository;
import jakarta.persistence.EntityManager;

public class ProductLongValueRepositoryImpl extends BaseRepositoryImpl<ProductLongValue, Long> implements ProductLongValueRepository {
    public ProductLongValueRepositoryImpl(EntityManager entityManager) {
        super(ProductLongValue.class, entityManager);
    }
}
