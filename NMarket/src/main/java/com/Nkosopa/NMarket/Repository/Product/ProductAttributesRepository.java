package com.Nkosopa.NMarket.Repository.Product;

import com.Nkosopa.NMarket.DTO.Product.ProductAttributesDTO;
import org.springframework.data.jpa.repository.JpaRepository;

import com.Nkosopa.NMarket.Entity.Product.ProductAttributes;

import java.util.List;

public interface ProductAttributesRepository extends JpaRepository<ProductAttributes, Long>{

}
