package com.Nkosopa.NMarket.Repository.Product.JPA;

import com.Nkosopa.NMarket.Entity.Product.ProductAttributes;
import com.Nkosopa.NMarket.Repository.Product.ProductAttributesRepository;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductAttributeJpaRepository extends JpaRepository<ProductAttributes, Long>, ProductAttributesRepository {
}
