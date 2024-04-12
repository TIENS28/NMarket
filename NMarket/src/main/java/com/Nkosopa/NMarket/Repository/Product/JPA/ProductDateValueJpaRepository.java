package com.Nkosopa.NMarket.Repository.Product.JPA;

import com.Nkosopa.NMarket.Entity.Product.ProductDateValue;
import com.Nkosopa.NMarket.Repository.Product.ProductDateTimeValueRepository;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;


public interface ProductDateValueJpaRepository extends CommonValueRepository<ProductDateValue, Long> {

    ProductDateValue findByAttributeId(Long attributeId);
}
