package com.Nkosopa.NMarket.Converter.Product;

import com.Nkosopa.NMarket.DTO.Product.AttributeDTO;
import com.Nkosopa.NMarket.Entity.DataType;
import com.Nkosopa.NMarket.Entity.Product.AttributeEAV;
import com.Nkosopa.NMarket.Entity.Product.ProductDateValue;
import com.Nkosopa.NMarket.Entity.Product.ProductLongValue;
import com.Nkosopa.NMarket.Entity.Product.ProductTextValue;
import com.Nkosopa.NMarket.Repository.Product.JPA.AttributeJPARepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class AttributeConverter {

    @Autowired
    private AttributeJPARepository attributeJPARepository;

    @Autowired
    private ProductValueConverter productValueConverter;

    public AttributeDTO mapToDTO(AttributeEAV attribute) {
        AttributeDTO attributeDTO = AttributeDTO.builder()
                .attributeName(attribute.getName())
                .textValues(productValueConverter.mapTextValuesToDTOs(attribute.getTextValues()))
                .intValues(productValueConverter.mapIntValuesToDTOs(attribute.getIntValues()))
                .dateValues(productValueConverter.mapDateValuesToDTOs(attribute.getDateValues()))
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
        attribute.setDataType(attributeDTO.getDataType());
        return attribute;
    }

    public List<AttributeEAV> mapToEntities(List<AttributeDTO> attributeDTOList) {
        List<AttributeEAV> attributeEntities = new ArrayList<>();

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
                attributeJPARepository.save(attributeEAV);
            }

            attributeEAV.setDataType(attributeDTO.getDataType());
            DataType dataType = attributeDTO.getDataType();

            switch (dataType) {
                case STRING:
                    List<ProductTextValue> textValues = productValueConverter.mapToProductTextValues(attributeDTO.getTextValues());
                    attributeEAV.setTextValues(textValues);
                    break;
                case LONG:
                    List<ProductLongValue> longValues = productValueConverter.mapToProductLongValues(attributeDTO.getIntValues());
                    attributeEAV.setIntValues(longValues);
                    break;
                case DATE:
                    List<ProductDateValue> dateValues = productValueConverter.mapToProductDateValues(attributeDTO.getDateValues());
                    attributeEAV.setDateValues(dateValues);
                    break;
                default:
                    break;
            }

            attributeEntities.add(attributeEAV);
        }

        return attributeEntities;
    }
}
