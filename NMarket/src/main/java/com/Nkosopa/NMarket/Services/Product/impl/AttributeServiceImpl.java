package com.Nkosopa.NMarket.Services.Product.impl;

import com.Nkosopa.NMarket.Converter.Product.AttributeConverter;
import com.Nkosopa.NMarket.Converter.Product.ProductConverter;
import com.Nkosopa.NMarket.DTO.Product.AttributeDTO;
import com.Nkosopa.NMarket.DTO.Product.ProductDTO;
import com.Nkosopa.NMarket.Entity.Product.AttributeEAV;
import com.Nkosopa.NMarket.Entity.Product.Product;
import com.Nkosopa.NMarket.Repository.Product.JPA.AttributeJPARepository;
import com.Nkosopa.NMarket.Repository.Product.JPA.ProductJpaRepository;
import com.Nkosopa.NMarket.Services.Product.iAttributeService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AttributeServiceImpl implements iAttributeService {

    @Autowired
    private AttributeJPARepository attributeJPARepository;

    @Autowired
    private ProductJpaRepository productJpaRepository;

    @Autowired
    private ProductConverter productConverter;

    @Autowired
    private AttributeConverter attributeConverter;

    @Transactional
    public ProductDTO addAttributeToProduct(Long productId, AttributeDTO attributeDTO) {
        // Retrieve the Product entity by productId
        Product product = productJpaRepository.findById(productId)
                .orElseThrow(() -> new IllegalArgumentException("Product not found with id: " + productId));

        // Convert AttributeDTO to AttributeEAV entity
        AttributeEAV attributeEAV = attributeConverter.mapToEntity(attributeDTO);

        // Associate the AttributeEAV with the Product
        product.getAttributeEAVS().add(attributeEAV);

        // Save the updated Product entity
        productJpaRepository.save(product);

        // Convert the updated Product entity to ProductDTO
        return productConverter.mapEntityToDTO(product);
    }

    @Transactional
    public void addAttributeToAllProducts(AttributeDTO attributeDTO) {
        List<Product> productList = productJpaRepository.findAll();
        AttributeEAV attributeEAV = attributeConverter.mapToEntity(attributeDTO);
        attributeEAV = attributeJPARepository.save(attributeEAV);
        for (Product product : productList) {
            product.getAttributeEAVS().add(attributeEAV);
        }
        productJpaRepository.saveAll(productList);
    }
}
