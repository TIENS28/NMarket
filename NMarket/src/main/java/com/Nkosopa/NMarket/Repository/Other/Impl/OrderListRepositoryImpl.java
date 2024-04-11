package com.Nkosopa.NMarket.Repository.Other.Impl;

import com.Nkosopa.NMarket.Entity.BaseEntity;
import com.Nkosopa.NMarket.Entity.Other.OrderList;
import com.Nkosopa.NMarket.Repository.BaseRepositoryImpl;
import com.Nkosopa.NMarket.Repository.Other.OrderListRepository;
import jakarta.persistence.EntityManager;

public class OrderListRepositoryImpl extends BaseRepositoryImpl<OrderList, Long> implements OrderListRepository {
    public OrderListRepositoryImpl(EntityManager entityManager) {
        super(OrderList.class, entityManager);
    }
}
