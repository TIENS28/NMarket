package com.Nkosopa.NMarket.Services.Product.impl;

import com.Nkosopa.NMarket.Converter.Product.AttributeConverter;
import com.Nkosopa.NMarket.Converter.Product.ProductConverter;
import com.Nkosopa.NMarket.DTO.Product.AttributeDTO;
import com.Nkosopa.NMarket.DTO.Product.ProductDTO;
import com.Nkosopa.NMarket.DTO.Product.ProductTypeDTO;
import com.Nkosopa.NMarket.Entity.DataType;
import com.Nkosopa.NMarket.Entity.Product.AttributeEAV;
import com.Nkosopa.NMarket.Entity.Product.Product;
import com.Nkosopa.NMarket.Entity.Product.ProductType;
import com.Nkosopa.NMarket.Repository.Product.JPA.AttributeJPARepository;
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
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements iProductService {

    @Autowired
    private ProductJpaRepository productJpaRepository;

    @Autowired
    private ProductConverter productConverter;

    @Autowired
    private ProductRepositoryImpl productRepository;

    @Autowired
    private AttributeJPARepository attributeJPARepository;

    @Autowired
    private AttributeConverter attributeConverter;

    @Autowired
    private ProductValueServiceImpl productValueService;

    @Override
    public List<ProductDTO> addProducts(List<ProductDTO> productDTOs) {
        List<ProductDTO> createdProducts = new ArrayList<>();
        List<String> skusToCheck = productDTOs.stream()
                .map(ProductDTO::getSku)
                .collect(Collectors.toList());

        List<String> existingSkus = productJpaRepository.findSkusInList(skusToCheck);

        for (ProductDTO productDTO : productDTOs) {
            String sku = productDTO.getSku();

            if (existingSkus.contains(sku)) {
                throw new IllegalArgumentException("SKU '" + sku + "' already exists. Duplicate SKUs not allowed.");
            }

            Product newProduct = new Product();
            newProduct.setName(productDTO.getName());
            newProduct.setSku(sku);
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
                newProduct.setAttributeEAVS(attributeConverter.mapToEntities(attributeDTOList));
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

    //search product
    @Override
    public Page<ProductDTO> searchProduct(String query, Pageable pageable) {
         Page<Product> productList = productJpaRepository.searchProducts(query, pageable);
         return productList.map(productConverter::mapEntityToDTO);
    }


    @Override
    public ProductDTO updateProduct(ProductDTO productDTO) {
        Optional<Product> productOptional = productJpaRepository.findById(productDTO.getId());

        return productOptional.map(product -> {
            product.setName(productDTO.getName());
            product.setSku(productDTO.getSku());
            product.setStock(productDTO.getStock());
            product.setPrice(productDTO.getPrice());
            product.setCurrency(productDTO.getCurrency());

            ProductTypeDTO productTypeDTO = productDTO.getProductTypeDTO();
            if (productTypeDTO != null) {
                ProductType productType = productJpaRepository.findProductTypeByType(productTypeDTO.getType());
                product.setProductType(productType);
            }
            if (productDTO.getAttributeDTOList() != null && !productDTO.getAttributeDTOList().isEmpty()) {
                List<AttributeDTO> attributeDTOList = productDTO.getAttributeDTOList();
                for (AttributeDTO attributeDTO : attributeDTOList) {
                    Optional<AttributeEAV> existingAttributeOpt = attributeJPARepository.findByName(attributeDTO.getAttributeName());

                    AttributeEAV attributeEAV;
                    if (existingAttributeOpt.isPresent()) {
                        attributeEAV = existingAttributeOpt.get();
                    } else {
                        attributeEAV = new AttributeEAV();
                        attributeEAV.setName(attributeDTO.getAttributeName());
                        attributeEAV.setSearchable(true);
                        attributeEAV.setDataType(attributeDTO.getDataType());
                    }

                    attributeEAV.setDataType(attributeDTO.getDataType());
                    DataType dataType = attributeDTO.getDataType();

                    switch (dataType) {
                        case STRING:
                            productValueService.updateTextValues(product.getId(), attributeEAV.getId(), attributeDTO.getTextValues());
                            break;
                        case LONG:
                            productValueService.updateLongValues(product.getId(), attributeEAV.getId(), attributeDTO.getIntValues());
                            break;
                        case DATE:
                            productValueService.updateDateValues(product.getId(), attributeEAV.getId(), attributeDTO.getDateValues());
                            break;
                        default:
                            break;
                    }
                    attributeJPARepository.save(attributeEAV);
                }
                product.setAttributeEAVS(attributeConverter.mapToEntities(attributeDTOList));
            }

            Product updatedProduct = productJpaRepository.save(product);
            return productConverter.mapEntityToDTO(updatedProduct);
        }).orElseThrow(() -> new EntityNotFoundException("Product not found"));
    }

}
