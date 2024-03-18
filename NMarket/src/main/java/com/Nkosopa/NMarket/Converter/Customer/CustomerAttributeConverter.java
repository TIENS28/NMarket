package com.Nkosopa.NMarket.Converter.Customer;

import com.Nkosopa.NMarket.DTO.Customer.CustomerAttributeDTO;
import com.Nkosopa.NMarket.DTO.Customer.CustomerDateValueDTO;
import com.Nkosopa.NMarket.DTO.Customer.CustomerLongValueDTO;
import com.Nkosopa.NMarket.DTO.Customer.CustomerTextValueDTO;
import com.Nkosopa.NMarket.Entity.Customer.*;
import com.Nkosopa.NMarket.Repository.Customer.JPA.CustomerAttributeJpaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class CustomerAttributeConverter {

    @Autowired
    private CustomerAttributeJpaRepository customerAttributeJpaRepository;

    public List<CustomerAttributeDTO> mapAttributesToDTOs(List<CustomerAttributes> customerAttributesList) {
        return customerAttributesList.stream()
                .map(this::mapAttributeToDTO)
                .collect(Collectors.toList());
    }

    public CustomerAttributeDTO mapAttributeToDTO(CustomerAttributes customerAttributes) {
        return CustomerAttributeDTO.builder()
                .attributeCode(customerAttributes.getAttribute_code())
                .attributeName(customerAttributes.getAttribute_name())
                .textValues(mapTextValuesToDTOs(customerAttributes.getTextValues()))
                .intValues(mapIntValuesToDTOs(customerAttributes.getIntValues()))
                .dateValues(mapDateValuesToDTOs(customerAttributes.getDateValues()))
                .dataType(customerAttributes.getDataType())
                .customerId(customerAttributes.getCustomer().getId())
                .build();
    }


    public List<CustomerTextValueDTO> mapTextValuesToDTOs(List<CustomerTextValue> textValues) {
        return textValues.stream()
                .map(textValue -> new CustomerTextValueDTO(textValue.getValue()))
                .collect(Collectors.toList());
    }

    public List<CustomerLongValueDTO> mapIntValuesToDTOs(List<CustomerLongValue> intValues) {
        return intValues.stream()
                .map(intValue -> new CustomerLongValueDTO(intValue.getValue()))
                .collect(Collectors.toList());
    }

    public List<CustomerDateValueDTO> mapDateValuesToDTOs(List<CustomerDateValue> dateValues) {
        return dateValues.stream()
                .map(dateValue -> new CustomerDateValueDTO(dateValue.getValue()))
                .collect(Collectors.toList());
    }

    public void convertCustomerAttributeDTOtoEntity(List<CustomerAttributeDTO> attributeDTOs, Customer customer) {
        for (com.Nkosopa.NMarket.DTO.Customer.CustomerAttributeDTO attributeDTO : attributeDTOs) {
            CustomerAttributes attributes = new CustomerAttributes();
            attributes.setAttribute_name(attributeDTO.getAttributeName());
            attributes.setAttribute_code(attributeDTO.getAttributeCode());
            attributes.setCustomer(customer);
            attributes.setDataType(attributeDTO.getDataType());
            customerAttributeJpaRepository.save(attributes);
        }
    }
}
