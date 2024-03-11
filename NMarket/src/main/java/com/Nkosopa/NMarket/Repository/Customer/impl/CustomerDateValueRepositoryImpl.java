package com.Nkosopa.NMarket.Repository.Customer.impl;

import com.Nkosopa.NMarket.Entity.Customer.CustomerDateValue;
import com.Nkosopa.NMarket.Repository.BaseRepositoryImpl;
import com.Nkosopa.NMarket.Repository.Customer.CustomerDateTimeRepository;

import jakarta.persistence.EntityManager;

public class CustomerDateValueRepositoryImpl extends BaseRepositoryImpl<CustomerDateValue, Long> implements CustomerDateTimeRepository{

	public CustomerDateValueRepositoryImpl(EntityManager entityManager) {
		super(CustomerDateValue.class, entityManager);
	}
	

}
