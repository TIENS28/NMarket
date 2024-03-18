package com.Nkosopa.NMarket.Repository.Product.JPA;

import com.Nkosopa.NMarket.Entity.Product.ProductTextValue;
import com.Nkosopa.NMarket.Repository.Product.ProductTextValueRepository;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductTextValueJpaRepository extends JpaRepository<ProductTextValue, Long>, ProductTextValueRepository {

}
