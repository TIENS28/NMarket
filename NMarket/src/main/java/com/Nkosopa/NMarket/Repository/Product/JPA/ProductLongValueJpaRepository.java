package com.Nkosopa.NMarket.Repository.Product.JPA;

import com.Nkosopa.NMarket.Entity.Product.ProductLongValue;

public interface ProductLongValueJpaRepository extends CommonValueRepository<ProductLongValue, Long> {

    ProductLongValue findByAttributeId(Long attributeId);
}
