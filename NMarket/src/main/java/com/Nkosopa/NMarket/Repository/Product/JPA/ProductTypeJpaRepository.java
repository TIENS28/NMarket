package com.Nkosopa.NMarket.Repository.Product.JPA;

import com.Nkosopa.NMarket.Entity.Product.ProductTextValue;
import com.Nkosopa.NMarket.Entity.Product.ProductType;
import com.Nkosopa.NMarket.Repository.Product.ProductTextValueRepository;
import com.Nkosopa.NMarket.Repository.Product.ProductTypeRepository;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductTypeJpaRepository extends JpaRepository<ProductType, Long>, ProductTypeRepository {
}
