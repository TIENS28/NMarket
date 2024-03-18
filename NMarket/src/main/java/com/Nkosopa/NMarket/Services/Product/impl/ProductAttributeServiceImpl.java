package com.Nkosopa.NMarket.Services.Product.impl;

import com.Nkosopa.NMarket.DTO.Product.ProductAttributesDTO;
import com.Nkosopa.NMarket.Entity.Product.Product;
import com.Nkosopa.NMarket.Entity.Product.ProductAttributes;
import com.Nkosopa.NMarket.Repository.Product.ProductAttributesRepository;
import com.Nkosopa.NMarket.Repository.Product.ProductJpaRepository;
import com.Nkosopa.NMarket.Services.Product.iProductAttributeService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ProductAttributeServiceImpl implements iProductAttributeService {

    @Autowired
    private ProductJpaRepository productJpaRepository;

    @Autowired
    private ProductAttributesRepository productAttributesRepository;
    @Override
    public void addProductAttribute(ProductAttributesDTO productAttributesDTO) {
        ProductAttributes attributes = new ProductAttributes();
        attributes.setAttribute_name(productAttributesDTO.getAttributeName());
        attributes.setAttribute_code(productAttributesDTO.getAttributeCode());

        Optional<Product> productOptional = productJpaRepository.findById(productAttributesDTO.getProductId());

        if (productOptional.isPresent()) {
            Product product = productOptional.get();
            attributes.setProduct(product);
            attributes.setDataType(productAttributesDTO.getDataType());
            productAttributesRepository.save(attributes);
        } else {
            throw new EntityNotFoundException("Product not found with ID: " + productAttributesDTO.getProductId());
        }

        attributes.setDataType(productAttributesDTO.getDataType());
        productAttributesRepository.save(attributes);
    }

}
