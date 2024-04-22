package com.Nkosopa.NMarket.Repository.Product.impl;

import com.Nkosopa.NMarket.Entity.DataType;
import com.Nkosopa.NMarket.Entity.Product.*;
import com.Nkosopa.NMarket.Repository.BaseRepositoryImpl;
import com.Nkosopa.NMarket.Repository.Product.ProductRepository;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.core.types.Predicate;
import jakarta.persistence.EntityManager;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Repository
public class ProductRepositoryImpl extends BaseRepositoryImpl<Product, Long> implements ProductRepository {

    public ProductRepositoryImpl(EntityManager entityManager) {
        super(Product.class, entityManager);
    }

    public List<Product> searchWithFilter(String searchTerm, Map<String, String> attributeFilters) {
        try {
            JPAQuery<Product> query = new JPAQuery<>(entityManager);
            QProduct qProduct = QProduct.product;

            BooleanExpression predicate = qProduct.name.containsIgnoreCase(searchTerm);

            for (Map.Entry<String, String> filter : attributeFilters.entrySet()) {
                String attributeName = filter.getKey();
                String attributeValue = filter.getValue();
                Predicate attributePredicate = createAttributePredicate(qProduct, attributeName, attributeValue);
                if (attributePredicate != null) {
                    predicate = predicate.and(attributePredicate);
                }
            }

            return query.select(qProduct)
                    .from(qProduct)
                    .where(predicate)
                    .fetch();
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    private Predicate createAttributePredicate(QProduct qProduct, String attributeName, String attributeValue) {
        QAttributeEAV qAttributeEAV = QAttributeEAV.attributeEAV;
        QProductTextValue qProductValue = QProductTextValue.productTextValue;
        QProductLongValue qProductLongValue = QProductLongValue.productLongValue;
        QProductDateValue qProductDateValue = QProductDateValue.productDateValue;
        switch (getDataTypeForAttribute(attributeName)) {
            case STRING:
                return qProduct.attributeEAVS.any().name.eq(attributeName)
                        .and(qProduct.textValues.any().value.eq(attributeValue));
            case LONG:
                Long longAttributeValue = Long.parseLong(attributeValue);
                return qProduct.attributeEAVS.any().name.eq(attributeName)
                        .and(qProduct.intValues.any().value.eq(longAttributeValue));
            default:
                return null;
        }
    }



    private DataType getDataTypeForAttribute(String attributeName) {
        return entityManager.createQuery(
                        "SELECT ae.dataType FROM AttributeEAV ae WHERE ae.name = :name", DataType.class)
                .setParameter("name", attributeName)
                .getSingleResult();
    }
}
