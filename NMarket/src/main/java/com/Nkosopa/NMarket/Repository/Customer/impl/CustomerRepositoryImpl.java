package com.Nkosopa.NMarket.Repository.Customer.impl;

import com.Nkosopa.NMarket.Entity.Customer.Customer;
import com.Nkosopa.NMarket.Repository.BaseRepositoryImpl;
import com.Nkosopa.NMarket.Repository.Customer.CustomerRepository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;

public class CustomerRepositoryImpl extends BaseRepositoryImpl<Customer, Long> implements CustomerRepository{

	private final JPAQueryFactory queryFactory;

	public CustomerRepositoryImpl(EntityManager entityManager, JPAQueryFactory queryFactory) {
		super(Customer.class, entityManager);
        this.queryFactory = queryFactory;
    }


}
