package com.Nkosopa.NMarket.Converter.Customer;

import com.Nkosopa.NMarket.DTO.Customer.*;
import com.Nkosopa.NMarket.DTO.Product.AttributeDTO;
import com.Nkosopa.NMarket.Entity.Customer.*;
import com.Nkosopa.NMarket.Entity.Product.AttributeEAV;
import com.Nkosopa.NMarket.Repository.Customer.JPA.CustomerAttributeEAVJPARepository;
import com.Nkosopa.NMarket.Repository.Customer.JPA.CustomerAttributeJpaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class CustomerAttributeConverter {

    @Autowired
    private CustomerAttributeJpaRepository customerAttributeJpaRepository;

    @Autowired
    private CustomerValueConverter customerValueConverter;

    @Autowired
    private CustomerAttributeEAVJPARepository customerAttributeEAVJPARepository;

    public CustomerAttributeEAVDTO mapToDTO(CustomerAttributeEAV attribute) {
        CustomerAttributeEAVDTO attributeDTO = CustomerAttributeEAVDTO.builder()
                .attributeName(attribute.getAttributeName())
                .textValues(customerValueConverter.mapTextValuesToDTOs(attribute.getTextValues()))
                .longValues(customerValueConverter.mapIntValuesToDTOs(attribute.getIntValues()))
                .dateValues(customerValueConverter.mapDateValuesToDTOs(attribute.getDateValues()))
                .dataType(attribute.getDataType())
                .build();
        attributeDTO.setId(attribute.getId());
        return attributeDTO;
    }

    public List<CustomerAttributeEAVDTO> mapToDTOs(List<CustomerAttributeEAV> attributes) {
        return attributes.stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }



    public CustomerAttributeEAV mapToEntity(CustomerAttributeEAVDTO attributeDTO) {
        CustomerAttributeEAV attribute = new CustomerAttributeEAV();
        attribute.setAttributeName(attributeDTO.getAttributeName());
        attribute.setId(attributeDTO.getId());
        attribute.setDataType(attributeDTO.getDataType());
        attribute.setTextValues(customerValueConverter.mapToCustomerTextValues(attributeDTO.getTextValues()));
        attribute.setIntValues(customerValueConverter.mapToCustomerLongValues(attributeDTO.getLongValues()));
        attribute.setDateValues(customerValueConverter.mapToCustomerDateValues(attributeDTO.getDateValues()));
        return attribute;
    }

    public List<CustomerAttributeEAV> mapToEntities(List<CustomerAttributeEAVDTO> attributeDTOList) {
        List<CustomerAttributeEAV> attributeEntities = new ArrayList<>();

        for (CustomerAttributeEAVDTO attributeDTO : attributeDTOList) {
            Optional<CustomerAttributeEAV> existingAttributeOpt = customerAttributeEAVJPARepository.findByAttributeName(attributeDTO.getAttributeName());
            CustomerAttributeEAV attributeEAV;

            if (existingAttributeOpt.isPresent()) {
                attributeEAV = existingAttributeOpt.get();
            } else {
                attributeEAV = new CustomerAttributeEAV();
                attributeEAV.setAttributeName(attributeDTO.getAttributeName());
                attributeEAV.setSearchable(true);
            }

            attributeEAV.setDataType(attributeDTO.getDataType());

            switch (attributeDTO.getDataType()) {
                case STRING:
                    attributeEAV.setTextValues(customerValueConverter.mapToCustomerTextValues(attributeDTO.getTextValues()));
                    break;
                case LONG:
                    attributeEAV.setIntValues(customerValueConverter.mapToCustomerLongValues(attributeDTO.getLongValues()));
                    break;
                case DATE:
                    attributeEAV.setDateValues(customerValueConverter.mapToCustomerDateValues(attributeDTO.getDateValues()));
                    break;
                default:
                    break;
            }
            customerAttributeEAVJPARepository.save(attributeEAV);
            attributeEntities.add(attributeEAV);
        }
        return attributeEntities;
    }
}
