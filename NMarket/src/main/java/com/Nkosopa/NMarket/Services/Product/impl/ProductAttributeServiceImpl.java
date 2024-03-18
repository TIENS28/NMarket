package com.Nkosopa.NMarket.Services.Product.impl;

import com.Nkosopa.NMarket.Converter.Product.ProductAttributeConverter;
import com.Nkosopa.NMarket.DTO.Product.ProductAttributesDTO;
import com.Nkosopa.NMarket.Entity.Customer.CustomerAttributes;
import com.Nkosopa.NMarket.Entity.Product.Product;
import com.Nkosopa.NMarket.Entity.Product.ProductAttributes;
import com.Nkosopa.NMarket.Repository.Product.JPA.*;
import com.Nkosopa.NMarket.Services.Product.iProductAttributeService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductAttributeServiceImpl implements iProductAttributeService {

    @Autowired
    private ProductJpaRepository productJpaRepository;

    @Autowired
    private ProductAttributeJpaRepository productAttributesJpaRepository;

    @Autowired
    private ProductAttributeConverter productAttributeConverter;

    @Autowired
    private ProductTextValueJpaRepository productTextValueJpaRepository;

    @Autowired
    private ProductLongValueJpaRepository productLongValueJpaRepository;

    @Autowired
    private ProductDateValueJpaRepository productDateValueJpaRepository;


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
            productAttributesJpaRepository.save(attributes);
        } else {
            throw new EntityNotFoundException("Product not found with ID: " + productAttributesDTO.getProductId());
        }

        attributes.setDataType(productAttributesDTO.getDataType());
        productAttributesJpaRepository.save(attributes);
    }

    public void addAttributeToAllProduct(List<ProductAttributesDTO> productAttributeDTOList){
        List<Product> products = productJpaRepository.findAll();

        for (Product product : products) {
            productAttributeConverter.convertProductAttributeDTOtoEntity(productAttributeDTOList, product);
        }
    }

    public void deleteSingleProductAttribute(Long productId, List<String> attributeCodes){
        List<ProductAttributes> productAttributes = productAttributesJpaRepository.findByProductIdAndAttributeCode(productId, attributeCodes);
        productAttributes.forEach(this::deleteAssociatedValues);
        productAttributesJpaRepository.deleteProductAttributeByIdAndAttributeCode(productId, attributeCodes);
    }

    private void deleteAssociatedValues(ProductAttributes productAttributes) {
        productTextValueJpaRepository.deleteAll(productAttributes.getTextValues());
        productLongValueJpaRepository.deleteAll(productAttributes.getIntValues());
        productDateValueJpaRepository.deleteAll(productAttributes.getDateValues());
    }//delete value for each customerAttributes

}
