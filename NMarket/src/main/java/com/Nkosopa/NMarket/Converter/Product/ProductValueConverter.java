package com.Nkosopa.NMarket.Converter.Product;

import com.Nkosopa.NMarket.DTO.Product.ProductDateValueDTO;
import com.Nkosopa.NMarket.DTO.Product.ProductLongValueDTO;
import com.Nkosopa.NMarket.DTO.Product.ProductTextValueDTO;
import com.Nkosopa.NMarket.DTO.Product.ProductValueDTO;
import com.Nkosopa.NMarket.Entity.BaseEntity;
import com.Nkosopa.NMarket.Entity.Product.AttributeEAV;
import com.Nkosopa.NMarket.Entity.Product.ProductDateValue;
import com.Nkosopa.NMarket.Entity.Product.ProductLongValue;
import com.Nkosopa.NMarket.Entity.Product.ProductTextValue;
import com.Nkosopa.NMarket.Repository.Product.JPA.AttributeJPARepository;
import com.Nkosopa.NMarket.Repository.Product.JPA.ProductDateValueJpaRepository;
import com.Nkosopa.NMarket.Repository.Product.JPA.ProductLongValueJpaRepository;
import com.Nkosopa.NMarket.Repository.Product.JPA.ProductTextValueJpaRepository;
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
    private ProductTextValueJpaRepository productTextValueJpaRepository;

    @Autowired
    private ProductLongValueJpaRepository productLongValueJpaRepository;

    @Autowired
    private ProductDateValueJpaRepository productDateValueJpaRepository;


    public List<ProductTextValueDTO> mapTextValuesToDTOs(List<ProductTextValue> textValues) {
        return textValues.stream()
                .map(textValue -> new ProductTextValueDTO(textValue.getValue(), textValue.getProduct().getId(), textValue.getAttribute().getId()))
                .collect(Collectors.toList());
    }

    public List<ProductLongValueDTO> mapIntValuesToDTOs(List<ProductLongValue> intValues) {
        return intValues.stream()
                .map(intValue -> new ProductLongValueDTO(intValue.getValue(), intValue.getProduct().getId(), intValue.getAttribute().getId()))
                .collect(Collectors.toList());
    }

    public List<ProductDateValueDTO> mapDateValuesToDTOs(List<ProductDateValue> dateValues) {
        return dateValues.stream()
                .map(dateValue -> new ProductDateValueDTO(dateValue.getValue(), dateValue.getProduct().getId(), dateValue.getAttribute().getId()))
                .collect(Collectors.toList());
    }

    public ProductTextValue mapToProductTextValue(ProductTextValueDTO dto) {
        ProductTextValue textValue = new ProductTextValue();
        textValue.setValue(dto.getValue());
        return textValue;
    }

    public ProductLongValue mapToProductLongValue(ProductLongValueDTO dto) {
        ProductLongValue longValue = new ProductLongValue();
        longValue.setValue(dto.getValue());
        return longValue;
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

    public ProductDateValue mapToProductDateValue(ProductDateValueDTO dto) {
        ProductDateValue dateValue = new ProductDateValue();
        dateValue.setValue(dto.getValue());
        return dateValue;
    }

    public List<ProductDateValue> mapToProductDateValues(List<ProductDateValueDTO> dtos) {
        return dtos.stream()
                .map(this::mapToProductDateValue)
                .collect(Collectors.toList());
    }

    
}
