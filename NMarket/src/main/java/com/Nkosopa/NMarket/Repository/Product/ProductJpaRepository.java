package com.Nkosopa.NMarket.Repository.Product;

import com.Nkosopa.NMarket.Entity.Product.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductJpaRepository extends JpaRepository<Product, Long>, ProductRepository {
}
