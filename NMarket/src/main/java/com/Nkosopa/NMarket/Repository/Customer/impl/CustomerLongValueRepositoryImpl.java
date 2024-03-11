package com.Nkosopa.NMarket.Repository.Customer.impl;

import com.Nkosopa.NMarket.Entity.Customer.CustomerLongValue;
import com.Nkosopa.NMarket.Repository.BaseRepositoryImpl;
import com.Nkosopa.NMarket.Repository.Customer.CustomerLongValueRepository;
import jakarta.persistence.EntityManager;

public class CustomerLongValueRepositoryImpl extends BaseRepositoryImpl<CustomerLongValue, Long> implements CustomerLongValueRepository {

    public CustomerLongValueRepositoryImpl(EntityManager entityManager) {
        super(CustomerLongValue.class, entityManager);
    }
}
