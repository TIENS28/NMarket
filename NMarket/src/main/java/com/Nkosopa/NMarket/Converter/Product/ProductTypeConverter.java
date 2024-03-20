package com.Nkosopa.NMarket.Converter.Product;

import com.Nkosopa.NMarket.DTO.Product.ProductAttributesDTO;
import com.Nkosopa.NMarket.DTO.Product.ProductDTO;
import com.Nkosopa.NMarket.DTO.Product.ProductTypeDTO;
import com.Nkosopa.NMarket.Entity.Product.Product;
import com.Nkosopa.NMarket.Entity.Product.ProductAttributes;
import com.Nkosopa.NMarket.Entity.Product.ProductType;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ProductTypeConverter {

    public ProductTypeDTO mapEntityToDTO(ProductType productType) {
        ProductTypeDTO productTypeDTO = new ProductTypeDTO();

        productTypeDTO.setId(productType.getId());
        productTypeDTO.setType(productTypeDTO.getType());
        return productTypeDTO;
    }

    public List<ProductTypeDTO> mapEntitiesToDTOs(List<ProductType> productTypeList) {
        return productTypeList.stream()
                .map(this::mapEntityToDTO)
                .collect(Collectors.toList());
    }
}
