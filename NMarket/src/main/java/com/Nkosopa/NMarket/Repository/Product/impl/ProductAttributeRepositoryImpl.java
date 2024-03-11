package com.Nkosopa.NMarket.Repository.Product.impl;

import com.Nkosopa.NMarket.Entity.Product.ProductAttributes;
import com.Nkosopa.NMarket.Repository.BaseRepositoryImpl;
import com.Nkosopa.NMarket.Repository.Product.ProductAttributesRepository;
import jakarta.persistence.EntityManager;

public class ProductAttributeRepositoryImpl extends BaseRepositoryImpl<ProductAttributes, Long> implements ProductAttributesRepository {
    public ProductAttributeRepositoryImpl(EntityManager entityManager) {
        super(ProductAttributes.class, entityManager);
    }


}
