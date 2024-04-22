package com.Nkosopa.NMarket.Repository.Product;

import com.Nkosopa.NMarket.Entity.Product.ProductType;
import org.springframework.data.jpa.repository.JpaRepository;

import com.Nkosopa.NMarket.Entity.Product.Product;
import org.springframework.data.jpa.repository.Query;

public interface ProductRepository extends JpaRepository<Product, Long>{

}
