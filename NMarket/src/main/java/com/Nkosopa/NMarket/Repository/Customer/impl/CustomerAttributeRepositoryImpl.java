package com.Nkosopa.NMarket.Repository.Customer.impl;

import com.Nkosopa.NMarket.DTO.Customer.CustomerAttributeDTO;
import com.Nkosopa.NMarket.Entity.Customer.*;
import com.Nkosopa.NMarket.Repository.BaseRepositoryImpl;
import com.Nkosopa.NMarket.Repository.Customer.CustomerAttributesRepository;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.CaseBuilder;
import com.querydsl.core.types.dsl.Coalesce;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class CustomerAttributeRepositoryImpl extends BaseRepositoryImpl<CustomerAttributes, Long>
		implements CustomerAttributesRepository {

	private final JPAQueryFactory queryFactory;

	public CustomerAttributeRepositoryImpl(EntityManager entityManager) {
		super(CustomerAttributes.class, entityManager);
		this.queryFactory = new JPAQueryFactory(entityManager);
	}


//	@Override
//	public List<CustomerAttributeDTO> getCustomerAttributes(Long customerId) {
//		QCustomerAttributes ca = QCustomerAttributes.customerAttributes;
//		QCustomerTextValue tv = QCustomerTextValue.customerTextValue;
//		QCustomerLongValue lv = QCustomerLongValue.customerLongValue;
//		QCustomerDateValue dv = QCustomerDateValue.customerDateValue;
//
//		return queryFactory
//				.select(Projections.constructor(CustomerAttributeDTO.class,
//						ca.attribute_code,
//						ca.attribute_name,
//						new Coalesce<>(tv.value, lv.value, dv.value).as("value")))
//				.from(ca)
//				.leftJoin(tv).on(ca.id.eq(tv.customerAttributes.id).and(ca.customer.id.eq(tv.customer.id)))
//				.leftJoin(lv).on(ca.id.eq(lv.customerAttributes.id).and(ca.customer.id.eq(lv.customer.id)))
//				.leftJoin(dv).on(ca.id.eq(dv.customerAttributes.id).and(ca.customer.id.eq(dv.customer.id)))
//				.where(ca.customer.id.eq(customerId))
//				.fetch();
//	}


}