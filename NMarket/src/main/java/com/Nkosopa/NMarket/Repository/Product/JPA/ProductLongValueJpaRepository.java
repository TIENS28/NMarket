package com.Nkosopa.NMarket.Repository.Product.JPA;

import com.Nkosopa.NMarket.Entity.Product.ProductLongValue;
import com.Nkosopa.NMarket.Repository.Product.ProductLongValueRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface ProductLongValueJpaRepository extends JpaRepository<ProductLongValue, Long>, ProductLongValueRepository {


    Optional<ProductLongValue> findByProductAttributesId(Long productAttributeId);
}
