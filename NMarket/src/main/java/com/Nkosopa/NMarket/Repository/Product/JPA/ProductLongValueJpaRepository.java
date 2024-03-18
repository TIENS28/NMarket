package com.Nkosopa.NMarket.Repository.Product.JPA;

import com.Nkosopa.NMarket.Entity.Product.ProductLongValue;
import com.Nkosopa.NMarket.Repository.Product.ProductLongValueRepository;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductLongValueJpaRepository extends JpaRepository<ProductLongValue, Long>, ProductLongValueRepository {

}
