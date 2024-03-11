package com.Nkosopa.NMarket.Repository.Product.impl;

import com.Nkosopa.NMarket.Entity.Product.ProductDateValue;
import com.Nkosopa.NMarket.Repository.BaseRepositoryImpl;
import com.Nkosopa.NMarket.Repository.Product.ProductDateTimeValueRepository;
import jakarta.persistence.EntityManager;

public class ProductDateTimeValueRepositoryImpl extends BaseRepositoryImpl<ProductDateValue, Long> implements ProductDateTimeValueRepository {
    public ProductDateTimeValueRepositoryImpl(EntityManager entityManager) {
        super(ProductDateValue.class, entityManager);
    }
}

