package com.Nkosopa.NMarket.Services.Product.impl;

import com.Nkosopa.NMarket.DTO.Product.ProductDTO;
import com.Nkosopa.NMarket.Entity.Product.Product;
import com.Nkosopa.NMarket.Repository.Product.ProductAttributesRepository;
import com.Nkosopa.NMarket.Repository.Product.JPA.ProductJpaRepository;
import com.Nkosopa.NMarket.Services.Product.iProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class ProductServiceImpl implements iProductService {

    @Autowired
    private ProductJpaRepository productJpaRepository;

    @Autowired
    private ProductAttributesRepository productAttributesRepository;

    @Override
    public void addProduct(ProductDTO productDTO) {
        Product newProduct = new Product();
        newProduct.setName(productDTO.getName());
        newProduct.setAttributes(new ArrayList<>());

        productJpaRepository.save(newProduct);
    }//add new customer manually

}
