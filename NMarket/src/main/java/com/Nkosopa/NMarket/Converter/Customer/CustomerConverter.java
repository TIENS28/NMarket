package com.Nkosopa.NMarket.Converter.Customer;

import com.Nkosopa.NMarket.DTO.Customer.CustomerAttributeDTO;
import com.Nkosopa.NMarket.DTO.Customer.CustomerDateValueDTO;
import com.Nkosopa.NMarket.DTO.Customer.CustomerLongValueDTO;
import com.Nkosopa.NMarket.DTO.Customer.CustomerTextValueDTO;
import com.Nkosopa.NMarket.Entity.Customer.CustomerAttributes;
import com.Nkosopa.NMarket.Entity.Customer.CustomerDateValue;
import com.Nkosopa.NMarket.Entity.Customer.CustomerLongValue;
import com.Nkosopa.NMarket.Entity.Customer.CustomerTextValue;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class CustomerConverter {
    public List<CustomerAttributeDTO> mapAttributesToDTOs(List<CustomerAttributes> customerAttributesList) {
        return customerAttributesList.stream()
                .map(this::mapAttributeToDTO)
                .collect(Collectors.toList());
    }

    private CustomerAttributeDTO mapAttributeToDTO(CustomerAttributes customerAttributes) {
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


    private List<CustomerTextValueDTO> mapTextValuesToDTOs(List<CustomerTextValue> textValues) {
        return textValues.stream()
                .map(textValue -> new CustomerTextValueDTO(textValue.getValue()))
                .collect(Collectors.toList());
    }

    private List<CustomerLongValueDTO> mapIntValuesToDTOs(List<CustomerLongValue> intValues) {
        return intValues.stream()
                .map(intValue -> new CustomerLongValueDTO(intValue.getValue()))
                .collect(Collectors.toList());
    }

    private List<CustomerDateValueDTO> mapDateValuesToDTOs(List<CustomerDateValue> dateValues) {
        return dateValues.stream()
                .map(dateValue -> new CustomerDateValueDTO(dateValue.getValue()))
                .collect(Collectors.toList());
    }
}
