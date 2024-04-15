package com.Nkosopa.NMarket.Converter.Customer;

import com.Nkosopa.NMarket.DTO.Customer.CustomerAttributeEAVDTO;
import com.Nkosopa.NMarket.Entity.Customer.CustomerAttributeEAV;
import com.Nkosopa.NMarket.Repository.Customer.JPA.CustomerAttributeEAVJPARepository;
import com.Nkosopa.NMarket.Repository.Customer.JPA.CustomerJPARepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class CustomerAttributeEAVConverter {

    @Autowired
    private CustomerJPARepository customerJPARepository;

    @Autowired
    private CustomerAttributeEAVJPARepository CustomerAttributeEAVJPARepository;

    @Autowired
    private CustomerValueConverter customerValueConverter;
    @Autowired
    private CustomerAttributeEAVJPARepository customerAttributeEAVJPARepository;

    public CustomerAttributeEAVDTO mapToDTO(CustomerAttributeEAV attribute) {
        CustomerAttributeEAVDTO customerAttributeEAVDTO = CustomerAttributeEAVDTO.builder()
                .attributeName(attribute.getAttributeName())
                .textValues(customerValueConverter.mapTextValuesToDTOs(attribute.getTextValues()))
                .longValues(customerValueConverter.mapIntValuesToDTOs(attribute.getIntValues()))
                .dateValues(customerValueConverter.mapDateValuesToDTOs(attribute.getDateValues()))
                .dataType(attribute.getDataType())
                .build();
        customerAttributeEAVDTO.setId(attribute.getId());
        return customerAttributeEAVDTO;
    }

    public List<CustomerAttributeEAVDTO> mapToDTOs(List<CustomerAttributeEAV> attributes) {
        return attributes.stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    public CustomerAttributeEAV mapToEntity(CustomerAttributeEAVDTO customerAttributeEAVDTO) {
        CustomerAttributeEAV attribute = new CustomerAttributeEAV();
        attribute.setAttributeName(customerAttributeEAVDTO.getAttributeName());
        attribute.setId(customerAttributeEAVDTO.getId());
        attribute.setDataType(customerAttributeEAVDTO.getDataType());
        attribute.setTextValues(customerValueConverter.mapToCustomerTextValues(customerAttributeEAVDTO.getTextValues()));
        attribute.setIntValues(customerValueConverter.mapToCustomerLongValues(customerAttributeEAVDTO.getLongValues()));
        attribute.setDateValues(customerValueConverter.mapToCustomerDateValues(customerAttributeEAVDTO.getDateValues()));
        return attribute;
    }

    public List<CustomerAttributeEAV> mapToEntities(List<CustomerAttributeEAVDTO> customerAttributeEAVDTOList) {
        List<CustomerAttributeEAV> attributeEntities = new ArrayList<>();

        for (CustomerAttributeEAVDTO customerAttributeEAVDTO : customerAttributeEAVDTOList) {
            Optional<CustomerAttributeEAV> existingAttributeOpt = CustomerAttributeEAVJPARepository.findByAttributeName(customerAttributeEAVDTO.getAttributeName());
            CustomerAttributeEAV customerAttributeEAV;

            if (existingAttributeOpt.isPresent()) {
                customerAttributeEAV = existingAttributeOpt.get();
            } else {
                customerAttributeEAV = new CustomerAttributeEAV();
                customerAttributeEAV.setAttributeName(customerAttributeEAVDTO.getAttributeName());
                customerAttributeEAV.setSearchable(true);
            }

            customerAttributeEAV.setDataType(customerAttributeEAVDTO.getDataType());

            switch (customerAttributeEAVDTO.getDataType()) {
                case STRING:
                    customerAttributeEAV.setTextValues(customerValueConverter.mapToCustomerTextValues(customerAttributeEAVDTO.getTextValues()));
                    break;
                case LONG:
                    customerAttributeEAV.setIntValues(customerValueConverter.mapToCustomerLongValues(customerAttributeEAVDTO.getLongValues()));
                    break;
                case DATE:
                    customerAttributeEAV.setDateValues(customerValueConverter.mapToCustomerDateValues(customerAttributeEAVDTO.getDateValues()));
                    break;
                default:
                    break;
            }
            customerAttributeEAVJPARepository.save(customerAttributeEAV);
            attributeEntities.add(customerAttributeEAV);
        }
        return attributeEntities;
    }
}
