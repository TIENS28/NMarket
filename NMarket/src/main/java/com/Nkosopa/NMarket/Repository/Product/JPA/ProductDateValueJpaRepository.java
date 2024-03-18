package com.Nkosopa.NMarket.Repository.Product.JPA;

import com.Nkosopa.NMarket.Entity.Product.ProductDateValue;
import com.Nkosopa.NMarket.Repository.Product.ProductDateTimeValueRepository;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductDateValueJpaRepository extends JpaRepository<ProductDateValue, Long>, ProductDateTimeValueRepository {

}
