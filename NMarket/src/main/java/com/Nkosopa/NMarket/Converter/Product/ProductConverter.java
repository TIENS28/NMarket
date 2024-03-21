package com.Nkosopa.NMarket.Converter.Product;

import com.Nkosopa.NMarket.Converter.Customer.CustomerAttributeConverter;
import com.Nkosopa.NMarket.DTO.Customer.CustomerDTO;
import com.Nkosopa.NMarket.DTO.Product.ProductAttributesDTO;
import com.Nkosopa.NMarket.DTO.Product.ProductDTO;
import com.Nkosopa.NMarket.Entity.Customer.Customer;
import com.Nkosopa.NMarket.Entity.Product.Product;
import com.Nkosopa.NMarket.Repository.Product.JPA.ProductAttributeJpaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ProductConverter {

    @Autowired
    private ProductAttributeJpaRepository productAttributeJpaRepository;

    @Autowired
    private ProductAttributeConverter productAttributeConverter;

    @Autowired
    private ProductTypeConverter productTypeConverter;

    public ProductDTO mapEntityToDTO(Product product) {
        ProductDTO productDTO = new ProductDTO();

        if (product.getId() != null) {
            productDTO.setId(product.getId());
        }

        productDTO.setName(product.getName());
        productDTO.setSku(product.getSku());
        productDTO.setStock(product.getStock());
        productDTO.setProductTypeDTOS(productTypeConverter.mapEntityToDTO(product.getProductType()));
        productDTO.setAttributesDTOS(productAttributeConverter.mapAttributesToDTOs(product.getAttributes()));

        return productDTO;
    }

}
