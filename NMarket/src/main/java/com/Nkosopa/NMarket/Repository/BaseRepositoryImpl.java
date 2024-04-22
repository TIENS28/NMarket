package com.Nkosopa.NMarket.Repository;

import org.springframework.data.jpa.repository.support.SimpleJpaRepository;

import com.Nkosopa.NMarket.Entity.Customer.QCustomer;
import com.Nkosopa.NMarket.Entity.Customer.QCustomerDateValue;
import com.Nkosopa.NMarket.Entity.Customer.QCustomerLongValue;
import com.Nkosopa.NMarket.Entity.Customer.QCustomerTextValue;
import com.Nkosopa.NMarket.Entity.Product.QProduct;
import com.Nkosopa.NMarket.Entity.Product.QProductDateValue;
import com.Nkosopa.NMarket.Entity.Product.QProductLongValue;
import com.Nkosopa.NMarket.Entity.Product.QProductTextValue;
import com.querydsl.jpa.impl.JPAQueryFactory;

import jakarta.persistence.EntityManager;

public abstract class BaseRepositoryImpl<T,ID> extends SimpleJpaRepository<T, ID> implements BaseRepository<T, ID>{

	public EntityManager entityManager;
	JPAQueryFactory jpaQueryFactory;
	
	protected final QCustomer customer = QCustomer.customer;
	protected final QCustomerLongValue customerLongValue = QCustomerLongValue.customerLongValue;
	protected final QCustomerTextValue customerTextValue = QCustomerTextValue.customerTextValue;
	protected final QCustomerDateValue customerDateValue = QCustomerDateValue.customerDateValue;
	
	protected final QProduct product = QProduct.product;
	protected QProductLongValue productLongValue = QProductLongValue.productLongValue;
	protected QProductTextValue productTextValue = QProductTextValue.productTextValue;
	protected QProductDateValue productDateValue =  QProductDateValue.productDateValue;
	
	
	public BaseRepositoryImpl(Class<T> domainClass, EntityManager entityManager) {
		super(domainClass, entityManager);
		this.entityManager =  entityManager;
		this.jpaQueryFactory = new JPAQueryFactory(entityManager);
	}

	@Override
	public T findByIDMandatory(ID id) throws IllegalArgumentException {
		return null;
	}

	protected EntityManager getEntityManager() {
		return entityManager;
	}
}
