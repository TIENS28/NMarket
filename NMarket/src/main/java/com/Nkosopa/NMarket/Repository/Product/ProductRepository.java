package com.Nkosopa.NMarket.Repository.Product;

import org.springframework.data.jpa.repository.JpaRepository;

import com.Nkosopa.NMarket.Entity.Product.Product;

public interface ProductRepository extends JpaRepository<Product, Long>{
}
