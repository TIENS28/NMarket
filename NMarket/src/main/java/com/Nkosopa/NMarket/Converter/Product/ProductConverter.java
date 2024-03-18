package com.Nkosopa.NMarket.Converter.Product;

import com.Nkosopa.NMarket.Converter.Customer.CustomerAttributeConverter;
import com.Nkosopa.NMarket.DTO.Customer.CustomerDTO;
import com.Nkosopa.NMarket.DTO.Product.ProductDTO;
import com.Nkosopa.NMarket.Entity.Customer.Customer;
import com.Nkosopa.NMarket.Entity.Product.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ProductConverter {


    @Autowired
    private ProductAttributeConverter productAttributeConverter;

    public ProductDTO mapEntityToDTO(Product product) {
        ProductDTO productDTO = new ProductDTO();

        if (product != null && product.getId() != null){
            productDTO.setId(product.getId());
            productDTO.setName(product.getName());
            productDTO.setSku(product.getSku());
            productDTO.setStock(product.getStock());
            productDTO.setAttributesDTOS(productAttributeConverter.mapAttributesToDTOs(product.getAttributes()));
        }

        return productDTO;
    }
}
