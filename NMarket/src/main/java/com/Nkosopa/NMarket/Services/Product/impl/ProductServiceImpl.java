package com.Nkosopa.NMarket.Services.Product.impl;

import com.Nkosopa.NMarket.Converter.Product.ProductAttributeConverter;
import com.Nkosopa.NMarket.Converter.Product.ProductConverter;
import com.Nkosopa.NMarket.DTO.Product.ProductAttributesDTO;
import com.Nkosopa.NMarket.DTO.Product.ProductDTO;
import com.Nkosopa.NMarket.Entity.Product.Product;
import com.Nkosopa.NMarket.Entity.Product.ProductAttributes;
import com.Nkosopa.NMarket.Repository.Product.JPA.ProductAttributeJpaRepository;
import com.Nkosopa.NMarket.Repository.Product.JPA.ProductJpaRepository;
import com.Nkosopa.NMarket.Services.Product.iProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements iProductService {

    @Autowired
    private ProductJpaRepository productJpaRepository;

    @Autowired
    private ProductAttributeJpaRepository productAttributeJpaRepository;

    @Autowired
    private ProductAttributeConverter productAttributeConverter;

    @Autowired
    private ProductConverter productConverter;

    @Override
    public void addProduct(ProductDTO productDTO) {
        Product newProduct = new Product();
        newProduct.setName(productDTO.getName());
        newProduct.setAttributes(new ArrayList<>());
        newProduct.setSku(productDTO.getSku());
        newProduct.setStock(productDTO.getStock());
        productJpaRepository.save(newProduct);
    }//add new customer manually

    @Override
    public Optional<ProductDTO> findProductById(Long productId) {
        Optional<Product> productOptional = productJpaRepository.findById(productId);

        return productOptional.map(customer -> {
            List<ProductAttributes> productAttributesList = productAttributeJpaRepository.getProductAtribute(productId);
            List<ProductAttributesDTO> attributeDTOs = productAttributeConverter.mapAttributesToDTOs(productAttributesList);

            return ProductDTO.builder()
                    .attributesDTOS(attributeDTOs)
                    .build();
        });
    }

    @Override
    public List<ProductDTO> getAllProduct() {
        return productJpaRepository.findAll().stream()
                .map(productConverter::mapEntityToDTO)
                .collect(Collectors.toList());
    }

    //search product
    @Override
    public Page<ProductDTO> searchProduct(String query, Pageable pageable) {
        return productJpaRepository.searchProducts(query, pageable)
                .map(productConverter::mapEntityToDTO);
    }


    @Override
    public Page<ProductDTO> listProduct(Pageable pageable) {
        return productJpaRepository.findAllProduct(pageable)
                .map(productConverter::mapEntityToDTO);
    }


}
