package com.Nkosopa.NMarket.Repository.Product.JPA;

import com.Nkosopa.NMarket.Entity.Product.ProductTextValue;
import com.Nkosopa.NMarket.Repository.Product.ProductTextValueRepository;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProductTextValueJpaRepository extends JpaRepository<ProductTextValue, Long>, ProductTextValueRepository {

    Optional<ProductTextValue> findByProductAttributesId(Long productAttributeId);
}
