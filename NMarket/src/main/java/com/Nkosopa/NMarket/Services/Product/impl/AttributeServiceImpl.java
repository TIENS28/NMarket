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
import java.util.Optional;
import java.util.stream.Collectors;

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
    public ProductDTO addAttributesToProduct(Long productId, List<AttributeDTO> attributeDTOList) {
        Product product = productJpaRepository.findById(productId)
                .orElseThrow(() -> new IllegalArgumentException("Product not found with id: " + productId));

        List<String> existingAttributeNames = product.getAttributeEAVS().stream()
                .map(AttributeEAV::getName)
                .toList();

        for (AttributeDTO attributeDTO : attributeDTOList) {
            String attributeName = attributeDTO.getAttributeName();
            if (!existingAttributeNames.contains(attributeName)) {
                Optional<AttributeEAV> attributeEAVOptional = attributeJPARepository.findByName(attributeName);

                if (attributeEAVOptional.isPresent()) {
                    AttributeEAV attributeEAV = attributeEAVOptional.get();
                    product.getAttributeEAVS().add(attributeEAV);
                } else {
                    AttributeEAV newAttributeEAV = new AttributeEAV();
                    newAttributeEAV.setName(attributeName);
                    newAttributeEAV.setDataType(attributeDTO.getDataType());
                    attributeJPARepository.save(newAttributeEAV);
                    product.getAttributeEAVS().add(newAttributeEAV);
                }
            }
        }
        productJpaRepository.save(product);
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
