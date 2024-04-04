package com.Nkosopa.NMarket.Services.Product.impl;

import com.Nkosopa.NMarket.Converter.Product.ProductAttributeConverter;
import com.Nkosopa.NMarket.Converter.Product.ProductConverter;
import com.Nkosopa.NMarket.Converter.Product.ProductTypeConverter;
import com.Nkosopa.NMarket.DTO.Product.ProductAttributesDTO;
import com.Nkosopa.NMarket.DTO.Product.ProductDTO;
import com.Nkosopa.NMarket.Entity.Product.Product;
import com.Nkosopa.NMarket.Entity.Product.ProductAttributes;
import com.Nkosopa.NMarket.Repository.Product.JPA.ProductAttributeJpaRepository;
import com.Nkosopa.NMarket.Repository.Product.JPA.ProductJpaRepository;
import com.Nkosopa.NMarket.Repository.Product.impl.ProductRepositoryImpl;
import com.Nkosopa.NMarket.Services.Product.iProductService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
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

    @Autowired
    private ProductRepositoryImpl productRepository;

    @Autowired
    private ProductTypeConverter productTypeConverter;


    @Override
    public List<ProductDTO> addProducts(List<ProductDTO> productDTOs) {
        List<ProductDTO> createdProducts = new ArrayList<>();
        for (ProductDTO productDTO : productDTOs) {
            Product newProduct = new Product();
            newProduct.setName(productDTO.getName());
            newProduct.setAttributes(new ArrayList<>());
            newProduct.setSku(productDTO.getSku());
            newProduct.setStock(productDTO.getStock());
            newProduct.setPrice(productDTO.getPrice());
            newProduct.setCurrency(productDTO.getCurrency());
            productJpaRepository.save(newProduct);
            createdProducts.add(productConverter.mapEntityToDTO(newProduct));
        }
        return createdProducts;
    }

    @Override
    public Optional<ProductDTO> findProductById(Long productId) {
        Optional<Product> productOptional = productJpaRepository.findById(productId);

        return productOptional.map(product -> {
            List<ProductAttributes> productAttributesList = productAttributeJpaRepository.getProductAtribute(productId);
            List<ProductAttributesDTO> attributeDTOs = productAttributeConverter.mapAttributesToDTOs(productAttributesList);

            return ProductDTO.builder()
                    .sku(product.getSku())
                    .productTypeDTOS(productTypeConverter.mapEntityToDTO(product.getProductType()))
                    .price(product.getPrice())
                    .stock(product.getStock())
                    .currency(product.getCurrency())
                    .name(product.getName())
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
    public Page<ProductDTO> searchProductWithFilter(String name, Map<String, String> filters, Pageable pageable) {
        Page<Product> products = productRepository.searchProductWithAttribute(name, filters, pageable);
        return products.map(productConverter::mapEntityToDTO);
    }


    @Override
    public Page<ProductDTO> listProduct(Pageable pageable) {
        return productJpaRepository.findAllProduct(pageable)
                .map(productConverter::mapEntityToDTO);
    }

    @Override
    public ProductDTO updateProduct(Long productId, ProductDTO productDTO) {
        Optional<Product> productOptional = productJpaRepository.findById(productId);

        return productOptional.map(product -> {
            product.setName(productDTO.getName());
            product.setSku(productDTO.getSku());
            product.setStock(productDTO.getStock());
            product.setPrice(productDTO.getPrice());
            product.setCurrency(productDTO.getCurrency());

            if (productDTO.getAttributesDTOS() != null && !productDTO.getAttributesDTOS().isEmpty()) {
                List<ProductAttributes> updatedAttributes = new ArrayList<>();
                for (ProductAttributesDTO attributeDTO : productDTO.getAttributesDTOS()) {
                    ProductAttributes attribute = productAttributeJpaRepository.findById(attributeDTO.getId())
                            .orElseThrow(() -> new RuntimeException("Attribute not found with ID: " + attributeDTO.getId()));

                    attribute.setAttribute_name(attributeDTO.getAttributeName());
                    attribute.setAttribute_code(attributeDTO.getAttributeCode());

                    updatedAttributes.add(attribute);
                }
                product.setAttributes(updatedAttributes);
            }

            Product updatedProduct = productJpaRepository.save(product);
            return productConverter.mapEntityToDTO(updatedProduct); // Convert updated product to DTO
        }).orElseThrow(() -> new EntityNotFoundException("Product not found with ID: " + productId));
    }

}
