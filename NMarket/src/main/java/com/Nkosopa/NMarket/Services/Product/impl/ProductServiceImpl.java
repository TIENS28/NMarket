package com.Nkosopa.NMarket.Services.Product.impl;

import com.Nkosopa.NMarket.Repository.Product.ProductAttributesRepository;
import com.Nkosopa.NMarket.Repository.Product.JPA.ProductJpaRepository;
import com.Nkosopa.NMarket.Services.Product.iProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductServiceImpl implements iProductService {

    @Autowired
    private ProductJpaRepository productJpaRepository;

    @Autowired
    private ProductAttributesRepository productAttributesRepository;



}
