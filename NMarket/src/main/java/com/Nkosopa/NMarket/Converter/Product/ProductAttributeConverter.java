package com.Nkosopa.NMarket.Converter.Product;

import com.Nkosopa.NMarket.DTO.Product.ProductAttributesDTO;
import com.Nkosopa.NMarket.DTO.Product.ProductDateValueDTO;
import com.Nkosopa.NMarket.DTO.Product.ProductLongValueDTO;
import com.Nkosopa.NMarket.DTO.Product.ProductTextValueDTO;
import com.Nkosopa.NMarket.Entity.Product.*;
import com.Nkosopa.NMarket.Repository.Product.JPA.ProductAttributeJpaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ProductAttributeConverter {

    @Autowired
    private ProductAttributeJpaRepository productAttributeJpaRepository;

    public List<ProductAttributesDTO> mapAttributesToDTOs(List<ProductAttributes> productAttributesList) {
        return productAttributesList.stream()
                .map(this::mapAttributeToDTO)
                .collect(Collectors.toList());
    }

    public ProductAttributesDTO mapAttributeToDTO(ProductAttributes productAttributes) {
        return ProductAttributesDTO.builder()
                .attributeCode(productAttributes.getAttribute_code())
                .attributeName(productAttributes.getAttribute_name())
                .textValues(mapTextValuesToDTOs(productAttributes.getTextValues()))
                .intValues(mapIntValuesToDTOs(productAttributes.getIntValues()))
                .dateValues(mapDateValuesToDTOs(productAttributes.getDateValues()))
                .dataType(productAttributes.getDataType())
                .productId(productAttributes.getProduct().getId())
                .build();
    }


    public List<ProductTextValueDTO> mapTextValuesToDTOs(List<ProductTextValue> textValues) {
        return textValues.stream()
                .map(textValue -> new ProductTextValueDTO(textValue.getValue()))
                .collect(Collectors.toList());
    }

    public List<ProductLongValueDTO> mapIntValuesToDTOs(List<ProductLongValue> intValues) {
        return intValues.stream()
                .map(intValue -> new ProductLongValueDTO(intValue.getValue()))
                .collect(Collectors.toList());
    }

    public List<ProductDateValueDTO> mapDateValuesToDTOs(List<ProductDateValue> dateValues) {
        return dateValues.stream()
                .map(dateValue -> new ProductDateValueDTO(dateValue.getValue()))
                .collect(Collectors.toList());
    }

    public void convertProductAttributeDTOtoEntity(List<ProductAttributesDTO> attributeDTOs, Product product) {
        for (ProductAttributesDTO attributeDTO : attributeDTOs) {
            ProductAttributes attributes = new ProductAttributes();
            attributes.setAttribute_name(attributeDTO.getAttributeName());
            attributes.setAttribute_code(attributeDTO.getAttributeCode());
            attributes.setProduct(product   );
            attributes.setDataType(attributeDTO.getDataType());
            productAttributeJpaRepository.save(attributes);
        }
    }


}
