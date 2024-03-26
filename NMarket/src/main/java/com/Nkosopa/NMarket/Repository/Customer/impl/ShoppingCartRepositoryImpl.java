package com.Nkosopa.NMarket.Repository.Customer.impl;

import com.Nkosopa.NMarket.Entity.Other.ShoppingCart;
import com.Nkosopa.NMarket.Repository.BaseRepositoryImpl;
import com.Nkosopa.NMarket.Repository.Customer.ShoppingCartRepository;
import jakarta.persistence.EntityManager;

public class ShoppingCartRepositoryImpl extends BaseRepositoryImpl<ShoppingCart, Long> implements ShoppingCartRepository {
    public ShoppingCartRepositoryImpl(EntityManager entityManager) {
        super(ShoppingCart.class, entityManager);
    }
}
