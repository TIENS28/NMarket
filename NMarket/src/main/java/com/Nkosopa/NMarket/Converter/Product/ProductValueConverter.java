package com.Nkosopa.NMarket.Converter.Product;

import com.Nkosopa.NMarket.DTO.Product.ProductDateValueDTO;
import com.Nkosopa.NMarket.DTO.Product.ProductLongValueDTO;
import com.Nkosopa.NMarket.DTO.Product.ProductTextValueDTO;
import com.Nkosopa.NMarket.DTO.Product.ProductValueDTO;
import com.Nkosopa.NMarket.Entity.BaseEntity;
import com.Nkosopa.NMarket.Entity.Product.*;
import com.Nkosopa.NMarket.Repository.Product.JPA.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class ProductValueConverter {

    @Autowired
    private AttributeJPARepository attributeJPARepository;

    @Autowired
    private ProductJpaRepository productJpaRepository;

    public ProductTextValueDTO mapTextValueToDTO(ProductTextValue productTextValue) {
        ProductTextValueDTO productTextValueDTO = new ProductTextValueDTO();
        if (productTextValueDTO.getId() != null) {
            productTextValueDTO.setId(productTextValue.getId());
        }
        productTextValueDTO.setValue(productTextValue.getValue());
        productTextValueDTO.setProductId(productTextValue.getProduct().getId());
        productTextValueDTO.setAttributeId(productTextValue.getAttribute().getId());
        return productTextValueDTO;
    }

    public ProductLongValueDTO mapLongValueToDTO(ProductLongValue productLongValue) {
        ProductLongValueDTO productLongValueDTO = new ProductLongValueDTO();
        if (productLongValue.getId() != null) {
            productLongValueDTO.setId(productLongValue.getId());
        }
        productLongValueDTO.setValue(productLongValue.getValue());
        productLongValueDTO.setProductId(productLongValue.getProduct().getId());
        productLongValueDTO.setAttributeId(productLongValue.getAttribute().getId());
        return productLongValueDTO;
    }

    public ProductDateValueDTO mapDateValueToDTO(ProductDateValue productDateValue) {
        ProductDateValueDTO productDateValueDTO = new ProductDateValueDTO();
        if (productDateValue.getId() != null) {
            productDateValueDTO.setId(productDateValue.getId());
        }
        productDateValueDTO.setValue(productDateValue.getValue());
        productDateValueDTO.setProductId(productDateValue.getProduct().getId());
        productDateValueDTO.setAttributeId(productDateValue.getAttribute().getId());
        return productDateValueDTO;
    }

    public List<ProductTextValueDTO> mapTextValuesToDTOs(List<ProductTextValue> textValues) {
        return textValues.stream()
                .map(this::mapTextValueToDTO)
                .collect(Collectors.toList());
    }

    public List<ProductLongValueDTO> mapIntValuesToDTOs(List<ProductLongValue> intValues) {
        return intValues.stream()
                .map(this::mapLongValueToDTO)
                .collect(Collectors.toList());
    }

    public List<ProductDateValueDTO> mapDateValuesToDTOs(List<ProductDateValue> dateValues) {
        return dateValues.stream()
                .map(this::mapDateValueToDTO)
                .collect(Collectors.toList());
    }

    public ProductTextValue mapToProductTextValue(ProductTextValueDTO dto) {
        ProductTextValue textValue = new ProductTextValue();
        textValue.setValue(dto.getValue());
        textValue.setAttribute(getAttribute(dto.getAttributeId()));
        textValue.setProduct(getProduct(dto.getProductId()));
        return textValue;
    }

    public ProductLongValue mapToProductLongValue(ProductLongValueDTO dto) {
        ProductLongValue longValue = new ProductLongValue();
        longValue.setValue(dto.getValue());
        longValue.setAttribute(getAttribute(dto.getAttributeId()));
        longValue.setProduct(getProduct(dto.getProductId()));
        return longValue;
    }


    public ProductDateValue mapToProductDateValue(ProductDateValueDTO dto) {
        ProductDateValue dateValue = new ProductDateValue();
        dateValue.setValue(dto.getValue());
        dateValue.setAttribute(getAttribute(dto.getAttributeId()));
        dateValue.setProduct(getProduct(dto.getProductId()));
        return dateValue;
    }

    public List<ProductLongValue> mapToProductLongValues(List<ProductLongValueDTO> dtos) {
        return dtos.stream()
                .map(this::mapToProductLongValue)
                .collect(Collectors.toList());
    }


    public List<ProductTextValue> mapToProductTextValues(List<ProductTextValueDTO> dtos) {
        return dtos.stream()
                .map(this::mapToProductTextValue)
                .collect(Collectors.toList());
    }

    public List<ProductDateValue> mapToProductDateValues(List<ProductDateValueDTO> dtos) {
        return dtos.stream()
                .map(this::mapToProductDateValue)
                .collect(Collectors.toList());
    }

    public AttributeEAV getAttribute(Long attributeId) {
        return attributeJPARepository.findById(attributeId)
                .orElseThrow(() -> new IllegalArgumentException("AttributeEAV not found"));
    }

    public Product getProduct(Long productId) {
        return productJpaRepository.findById(productId)
                .orElseThrow(() -> new IllegalArgumentException("Product not found"));
    }

}
