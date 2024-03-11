package com.Nkosopa.NMarket.Repository.Customer.impl;

import com.Nkosopa.NMarket.Entity.Customer.CustomerTextValue;
import com.Nkosopa.NMarket.Repository.BaseRepositoryImpl;
import com.Nkosopa.NMarket.Repository.Customer.CustomerTextValueRepository;
import jakarta.persistence.EntityManager;

public class CustomerTextValueRepositoryImpl extends BaseRepositoryImpl<CustomerTextValue, Long> implements CustomerTextValueRepository {

    public CustomerTextValueRepositoryImpl(EntityManager entityManager) {
        super(CustomerTextValue.class, entityManager);
    }
}
