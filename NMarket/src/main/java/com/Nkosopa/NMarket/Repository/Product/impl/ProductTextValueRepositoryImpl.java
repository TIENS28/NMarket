package com.Nkosopa.NMarket.Repository.Product.impl;

import com.Nkosopa.NMarket.Entity.Product.ProductTextValue;
import com.Nkosopa.NMarket.Repository.BaseRepositoryImpl;
import com.Nkosopa.NMarket.Repository.Product.ProductTextValueRepository;
import jakarta.persistence.EntityManager;

public class ProductTextValueRepositoryImpl extends BaseRepositoryImpl<ProductTextValue, Long> implements ProductTextValueRepository {

    public ProductTextValueRepositoryImpl(EntityManager entityManager) {
        super(ProductTextValue.class, entityManager);
    }
}
