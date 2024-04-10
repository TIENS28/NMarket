package com.Nkosopa.NMarket.Converter.Product;

import com.Nkosopa.NMarket.DTO.Product.AttributeDTO;
import com.Nkosopa.NMarket.DTO.Product.ProductDateValueDTO;
import com.Nkosopa.NMarket.DTO.Product.ProductLongValueDTO;
import com.Nkosopa.NMarket.DTO.Product.ProductTextValueDTO;
import com.Nkosopa.NMarket.Entity.Product.AttributeEAV;
import com.Nkosopa.NMarket.Entity.Product.ProductDateValue;
import com.Nkosopa.NMarket.Entity.Product.ProductLongValue;
import com.Nkosopa.NMarket.Entity.Product.ProductTextValue;
import org.springframework.stereotype.Component;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class AttributeConverter {

    public AttributeDTO mapToDTO(AttributeEAV attribute) {
        AttributeDTO attributeDTO = AttributeDTO.builder()
                .attributeName(attribute.getName())
                .textValues(mapTextValuesToDTOs(attribute.getTextValues()))
                .intValues(mapIntValuesToDTOs(attribute.getIntValues()))
                .dateValues(mapDateValuesToDTOs(attribute.getDateValues()))
                .dataType(attribute.getDataType())
                .build();
        attributeDTO.setId(attribute.getId());
        return attributeDTO;
    }

    public List<AttributeDTO> mapToDTOs(List<AttributeEAV> attributes) {
        return attributes.stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    public AttributeEAV mapToEntity(AttributeDTO attributeDTO) {
        AttributeEAV attribute = new AttributeEAV();
        attribute.setName(attributeDTO.getAttributeName());
        attribute.setId(attributeDTO.getId());
        return attribute;
    }

    public List<AttributeEAV> mapToEntities(List<AttributeDTO> attributeDTOs) {
        return attributeDTOs.stream()
                .map(this::mapToEntity)
                .collect(Collectors.toList());
    }

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
}
