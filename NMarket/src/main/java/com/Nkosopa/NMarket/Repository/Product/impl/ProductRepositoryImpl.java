package com.Nkosopa.NMarket.Repository.Product.impl;

import com.Nkosopa.NMarket.Entity.DataType;
import com.Nkosopa.NMarket.Entity.Product.Product;
import com.Nkosopa.NMarket.Entity.Product.ProductAttributes;
import com.Nkosopa.NMarket.Entity.Product.QProduct;
import com.Nkosopa.NMarket.Entity.Product.QProductAttributes;
import com.Nkosopa.NMarket.Entity.Product.QProductDateValue;
import com.Nkosopa.NMarket.Entity.Product.QProductLongValue;
import com.Nkosopa.NMarket.Entity.Product.QProductTextValue;
import com.Nkosopa.NMarket.Repository.BaseRepositoryImpl;
import com.Nkosopa.NMarket.Repository.Product.JPA.ProductJpaRepository;
import com.Nkosopa.NMarket.Repository.Product.ProductRepository;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQuery;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class ProductRepositoryImpl extends BaseRepositoryImpl<Product, Long> implements ProductRepository {
    public ProductRepositoryImpl(EntityManager entityManager) {
        super(Product.class, entityManager);
    }

    private final QProduct qProduct = QProduct.product;
    private final QProductAttributes qProductAttributes = QProductAttributes.productAttributes;
    private final QProductTextValue qProductTextValue = QProductTextValue.productTextValue;
    private final QProductLongValue qProductLongValue = QProductLongValue.productLongValue;
    private final QProductDateValue qProductDateValue = QProductDateValue.productDateValue;


    public Page<Product> searchProductWithAttribute(String name, Map<String, String> filters, Pageable pageable) {
        JPAQuery<Product> query = new JPAQuery<>(entityManager);

        query.select(qProduct)
                .from(qProduct)
                .leftJoin(qProduct.attributes, qProductAttributes)
                .leftJoin(qProductAttributes.textValues, qProductTextValue)
                .leftJoin(qProductAttributes.intValues, qProductLongValue)
                .leftJoin(qProductAttributes.dateValues, qProductDateValue);

        BooleanBuilder where = new BooleanBuilder();

        if (name != null && !name.isEmpty()) {
            where.and(qProduct.name.like(name));
        }

        if (filters != null) {
            for (Map.Entry<String, String> entry : filters.entrySet()) {
                String attributeCode = entry.getKey();
                String attributeValue = entry.getValue();

                BooleanBuilder attributePredicate = new BooleanBuilder();

                DataType dataType = getDataTypeForAttribute(attributeCode);

                switch (dataType) {
                    case STRING:
                        attributePredicate.and(qProductAttributes.attribute_code.eq(attributeCode)
                                .and(qProductTextValue.value.eq(attributeValue)));
                        break;
                    case LONG:
                        Long longValue = Long.parseLong(attributeValue);
                        attributePredicate.and(qProductAttributes.attribute_code.eq(attributeCode)
                                .and(qProductLongValue.value.eq(longValue)));
                        break;
                    default:
                        throw new IllegalArgumentException("Unsupported data type for attribute: " + dataType);
                }

                where.and(attributePredicate);
            }
        }

        query.where(where);

        List<Product> products = query.fetch();

        int start = (int) pageable.getOffset();
        int end = (start + pageable.getPageSize()) > products.size() ? products.size() : (start + pageable.getPageSize());
        List<Product> paginatedProducts = products.subList(start, end);

        return new PageImpl<>(paginatedProducts, pageable, products.size());
    }

    public DataType getDataTypeForAttribute(String attributeCode) {
        String jpql = "SELECT dataType FROM ProductAttributes WHERE attribute_code = :attributeCode";
        Query query = entityManager.createQuery(jpql, DataType.class);
        query.setParameter("attributeCode", attributeCode);

        return (DataType) query.getSingleResult();
    }
}
