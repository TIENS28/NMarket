package com.Nkosopa.NMarket.Services.Product.impl;

import com.Nkosopa.NMarket.Converter.Product.AttributeConverter;
import com.Nkosopa.NMarket.Converter.Product.ProductAttributeConverter;
import com.Nkosopa.NMarket.Converter.Product.ProductConverter;
import com.Nkosopa.NMarket.Converter.Product.ProductTypeConverter;
import com.Nkosopa.NMarket.DTO.Product.AttributeDTO;
import com.Nkosopa.NMarket.DTO.Product.ProductAttributesDTO;
import com.Nkosopa.NMarket.DTO.Product.ProductDTO;
import com.Nkosopa.NMarket.DTO.Product.ProductTypeDTO;
import com.Nkosopa.NMarket.Entity.Product.*;
import com.Nkosopa.NMarket.Repository.Product.JPA.*;
import com.Nkosopa.NMarket.Repository.Product.impl.ProductRepositoryImpl;
import com.Nkosopa.NMarket.Services.Product.iProductService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
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
    private ProductTypeJpaRepository productTypeJpaRepository;

    @Autowired
    private ProductConverter productConverter;

    @Autowired
    private ProductRepositoryImpl productRepository;

    @Autowired
    private ProductTypeConverter productTypeConverter;

    @Autowired
    private AttributeJPARepository attributeJPARepository;

    @Autowired
    private AttributeConverter attributeConverter;


    @Override
    public List<ProductDTO> addProducts(List<ProductDTO> productDTOs) {
        List<ProductDTO> createdProducts = new ArrayList<>();
        for (ProductDTO productDTO : productDTOs) {
            Product newProduct = new Product();
            newProduct.setName(productDTO.getName());
            newProduct.setSku(productDTO.getSku());
            newProduct.setStock(productDTO.getStock());
            newProduct.setPrice(productDTO.getPrice());
            newProduct.setCurrency(productDTO.getCurrency());

            ProductTypeDTO productTypeDTO = productDTO.getProductTypeDTO();
            if (productTypeDTO != null) {
                ProductType productType = productJpaRepository.findProductTypeByType(productTypeDTO.getType());
                newProduct.setProductType(productType);
            }

            List<AttributeDTO> attributeDTOList = productDTO.getAttributeDTOList();
            if (attributeDTOList != null && !attributeDTOList.isEmpty()) {
                List<AttributeEAV> attributes = attributeDTOList.stream()
                        .map(attributeConverter::mapToEntity)
                        .collect(Collectors.toList());
                newProduct.setAttributeEAVS(attributes);
            }

            productJpaRepository.save(newProduct);

            createdProducts.add(productConverter.mapEntityToDTO(newProduct));
        }
        return createdProducts;
    }


    @Override
    public Optional<ProductDTO> findProductById(Long productId) {
        Optional<Product> productOptional = productJpaRepository.findById(productId);
        return productOptional.map(product -> {
            return productConverter.mapEntityToDTO(product);
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
         Page<Product> productList = productJpaRepository.searchProducts2(query, pageable);
         return productList.map(productConverter::mapEntityToDTO);
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

    @Override //unfixed
    public ProductDTO updateProduct(ProductDTO productDTO) {
        Optional<Product> productOptional = productJpaRepository.findById(productDTO.getId());

        return productOptional.map(product -> {
            product.setName(productDTO.getName());
            product.setSku(productDTO.getSku());
            product.setStock(productDTO.getStock());
            product.setPrice(productDTO.getPrice());
            product.setCurrency(productDTO.getCurrency());

            if (productDTO.getAttributeDTOList() != null && !productDTO.getAttributeDTOList().isEmpty()) {
                List<AttributeEAV> updatedAttributes = attributeConverter.mapToEntities(productDTO.getAttributeDTOList());
                product.setAttributeEAVS(updatedAttributes);
            }

            Product updatedProduct = productJpaRepository.save(product);
            return productConverter.mapEntityToDTO(updatedProduct); // Convert updated product to DTO
        }).orElseThrow(() -> new EntityNotFoundException("Product not found"));
    }

    public ProductDTO updateProduct2(ProductDTO productDTO) {
        Product product = productConverter.mapDTOToEntity(productDTO);
        if (productDTO.getAttributeDTOList() != null && !productDTO.getAttributeDTOList().isEmpty()) {
            List<AttributeEAV> updatedAttributes = attributeConverter.mapToEntities(productDTO.getAttributeDTOList());
            product.setAttributeEAVS(updatedAttributes);
        }
        Product updatedProduct = productJpaRepository.save(product);
        return productConverter.mapEntityToDTO(updatedProduct);
    }

}
