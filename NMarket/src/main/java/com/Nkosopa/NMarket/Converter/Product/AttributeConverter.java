package com.Nkosopa.NMarket.Converter.Product;

import com.Nkosopa.NMarket.DTO.Product.AttributeDTO;
import com.Nkosopa.NMarket.Entity.DataType;
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
        attribute.setTextValues(productValueConverter.mapToProductTextValues(attributeDTO.getTextValues()));
        attribute.setIntValues(productValueConverter.mapToProductLongValues(attributeDTO.getIntValues()));
        attribute.setDateValues(productValueConverter.mapToProductDateValues(attributeDTO.getDateValues()));
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
            }

            attributeEAV.setDataType(attributeDTO.getDataType());

            switch (attributeDTO.getDataType()) {
                case STRING:
                    attributeEAV.setTextValues(productValueConverter.mapToProductTextValues(attributeDTO.getTextValues()));
                    break;
                case LONG:
                    attributeEAV.setIntValues(productValueConverter.mapToProductLongValues(attributeDTO.getIntValues()));
                    break;
                case DATE:
                    attributeEAV.setDateValues(productValueConverter.mapToProductDateValues(attributeDTO.getDateValues()));
                    break;
                default:
                    break;
            }
            attributeJPARepository.save(attributeEAV);
            attributeEntities.add(attributeEAV);
        }
        return attributeEntities;
    }

}
