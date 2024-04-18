package com.Nkosopa.NMarket.Converter.Customer;

import com.Nkosopa.NMarket.DTO.Customer.CustomerDateValueDTO;
import com.Nkosopa.NMarket.DTO.Customer.CustomerLongValueDTO;
import com.Nkosopa.NMarket.DTO.Customer.CustomerTextValueDTO;
import com.Nkosopa.NMarket.Entity.Customer.*;
import com.Nkosopa.NMarket.Repository.Customer.JPA.CustomerAttributeEAVJPARepository;
import com.Nkosopa.NMarket.Repository.Customer.JPA.CustomerJPARepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class CustomerValueConverter {

    @Autowired
    private CustomerAttributeEAVJPARepository attributeJPARepository;

    @Autowired
    private CustomerJPARepository customerJPARepository;

    public CustomerTextValueDTO mapTextValueToDTO(CustomerTextValue customerTextValue){
        CustomerTextValueDTO customerTextValueDTO = new CustomerTextValueDTO();
        customerTextValueDTO.setId(customerTextValue.getId());
        customerTextValueDTO.setValue(customerTextValue.getValue());
        return customerTextValueDTO;
    }

    public CustomerLongValueDTO mapLongValueToDTO(CustomerLongValue customerLongValue){
        CustomerLongValueDTO customerLongValueDTO = new CustomerLongValueDTO();
        customerLongValueDTO.setId(customerLongValue.getId());
        customerLongValueDTO.setValue(customerLongValue.getValue());
        return customerLongValueDTO;
    }

    public CustomerDateValueDTO mapDateValueToDTO(CustomerDateValue customerDateValue){
        CustomerDateValueDTO customerDateValueDTO = new CustomerDateValueDTO();
        customerDateValueDTO.setId(customerDateValue.getId());
        customerDateValueDTO.setValue(customerDateValue.getValue());
        return customerDateValueDTO;
    }

    public List<CustomerTextValueDTO> mapTextValuesToDTOs(List<CustomerTextValue> textValues) {
        return textValues.stream()
                .map(this::mapTextValueToDTO)
                .collect(Collectors.toList());
    }

    public List<CustomerLongValueDTO> mapIntValuesToDTOs(List<CustomerLongValue> intValues) {
        return intValues.stream()
                .map(this::mapLongValueToDTO)
                .collect(Collectors.toList());
    }

    public List<CustomerDateValueDTO> mapDateValuesToDTOs(List<CustomerDateValue> dateValues) {
        return dateValues.stream()
                .map(this::mapDateValueToDTO)
                .collect(Collectors.toList());
    }

    public CustomerTextValue mapToCustomerTextValue(CustomerTextValueDTO dto) {
        CustomerTextValue textValue = new CustomerTextValue();
        textValue.setValue(dto.getValue());
        return textValue;
    }

    public CustomerLongValue mapToCustomerLongValue(CustomerLongValueDTO dto) {
        CustomerLongValue longValue = new CustomerLongValue();
        longValue.setValue(dto.getValue());
        return longValue;
    }


    public CustomerDateValue mapToCustomerDateValue(CustomerDateValueDTO dto) {
        CustomerDateValue dateValue = new CustomerDateValue();
        dateValue.setValue(dto.getValue());
        return dateValue;
    }

    public List<CustomerLongValue> mapToCustomerLongValues(List<CustomerLongValueDTO> dtos) {
        return dtos.stream()
                .map(this::mapToCustomerLongValue)
                .collect(Collectors.toList());
    }


    public List<CustomerTextValue> mapToCustomerTextValues(List<CustomerTextValueDTO> dtos) {
        return dtos.stream()
                .map(this::mapToCustomerTextValue)
                .collect(Collectors.toList());
    }

    public List<CustomerDateValue> mapToCustomerDateValues(List<CustomerDateValueDTO> dtos) {
        return dtos.stream()
                .map(this::mapToCustomerDateValue)
                .collect(Collectors.toList());
    }

    public CustomerAttributeEAV getAttribute(Long attributeId) {
        return attributeJPARepository.findById(attributeId)
                .orElseThrow(() -> new IllegalArgumentException("AttributeEAV not found"));
    }

    public Customer getCustomer(Long CustomerId) {
        return customerJPARepository.findById(CustomerId)
                .orElseThrow(() -> new IllegalArgumentException("Customer not found"));
    }
}
