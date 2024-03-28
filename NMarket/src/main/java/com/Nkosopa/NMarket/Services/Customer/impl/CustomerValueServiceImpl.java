package com.Nkosopa.NMarket.Services.Customer.impl;

import com.Nkosopa.NMarket.Converter.Customer.CustomerAttributeConverter;
import com.Nkosopa.NMarket.DTO.Customer.CustomerValueDTO;
import com.Nkosopa.NMarket.Entity.Customer.CustomerAttributes;
import com.Nkosopa.NMarket.Entity.Customer.CustomerDateValue;
import com.Nkosopa.NMarket.Entity.Customer.CustomerLongValue;
import com.Nkosopa.NMarket.Entity.Customer.CustomerTextValue;
import com.Nkosopa.NMarket.Entity.DataType;
import com.Nkosopa.NMarket.Repository.Customer.JPA.CustomerAttributeJpaRepository;
import com.Nkosopa.NMarket.Repository.Customer.JPA.CustomerDateValueJpaRepository;
import com.Nkosopa.NMarket.Repository.Customer.JPA.CustomerLongValueJpaRepository;
import com.Nkosopa.NMarket.Repository.Customer.JPA.CustomerTextValueJpaRepository;
import com.Nkosopa.NMarket.Services.Customer.iCustomerValueService;
import jakarta.persistence.EntityNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class CustomerValueServiceImpl implements iCustomerValueService {

    @Autowired
    private CustomerAttributeJpaRepository customerAttributeJpaRepository;

    @Autowired
    private CustomerTextValueJpaRepository customerTextValueJpaRepository;

    @Autowired
    private CustomerLongValueJpaRepository customerLongValueJpaRepository;

    @Autowired
    private CustomerDateValueJpaRepository customerDateValueJpaRepository;

    @Autowired
    private CustomerAttributeConverter customerAttributeConverter;
    private static final Logger logger = LoggerFactory.getLogger(CustomerServiceImpl.class);

    @Override
    public CustomerValueDTO addValueToCustomerAttribute(Long customerAttributeId, CustomerValueDTO valueDTO) {
        CustomerAttributes customerAttribute = customerAttributeJpaRepository.findById(customerAttributeId)
                .orElseThrow(() -> new EntityNotFoundException("Attribute not found"));

        DataType dataType = customerAttribute.getDataType();

        switch (dataType) {
            case STRING:
                CustomerTextValue textValue = new CustomerTextValue();
                textValue.setValue(valueDTO.getValue());
                textValue.setCustomerAttributes(customerAttribute);
                textValue.setCustomer(customerAttribute.getCustomer());
                customerTextValueJpaRepository.save(textValue);
                return new CustomerValueDTO(textValue.getValue());
            case LONG:
                CustomerLongValue longValue = new CustomerLongValue();
                longValue.setValue(Long.parseLong(valueDTO.getValue()));
                longValue.setCustomerAttributes(customerAttribute);
                longValue.setCustomer(customerAttribute.getCustomer());
                customerLongValueJpaRepository.save(longValue);
                return new CustomerValueDTO(String.valueOf(longValue.getValue()));
            case DATE:
                CustomerDateValue dateValue = new CustomerDateValue();
                dateValue.setValue(parseStringToDate(valueDTO.getValue()));
                dateValue.setCustomerAttributes(customerAttribute);
                dateValue.setCustomer(customerAttribute.getCustomer());
                customerDateValueJpaRepository.save(dateValue);
                return new CustomerValueDTO(dateValue.getValue().toString());
            default:
                throw new IllegalArgumentException("Unsupported data type");
        }
    }

    @Override
    public List<CustomerValueDTO> addValuesToCustomerAttributes(List<CustomerValueDTO> valueDTOs) {
        List<CustomerValueDTO> savedValues = new ArrayList<>();
        for (CustomerValueDTO valueDTO : valueDTOs) {
            savedValues.add(addValueToCustomerAttribute(valueDTO.getAttributeId(), valueDTO));
        }
        return savedValues;
    }

    @Override
    public CustomerValueDTO updateCustomerAttributeValue(Long attributeId, CustomerValueDTO customerValueDTO) {
        CustomerAttributes customerAttribute = customerAttributeJpaRepository.findById(attributeId)
                .orElseThrow(() -> new EntityNotFoundException("Attribute not found"));

        DataType dataType = customerAttribute.getDataType();
        switch (dataType) {
            case STRING:
                return updateTextValue(customerAttribute, customerValueDTO);
            case LONG:
                return updateLongValue(customerAttribute, customerValueDTO);
            case DATE:
                return updateDateValue(customerAttribute, customerValueDTO);
            default:
                throw new IllegalArgumentException("Unsupported data type");
        }
    }

    private CustomerValueDTO updateTextValue(CustomerAttributes customerAttribute, CustomerValueDTO customerValueDTO) {
        return customerTextValueJpaRepository.findByCustomerAttributesId(customerAttribute.getId())
                .map(textValue -> {
                    textValue.setValue(customerValueDTO.getValue());
                    return new CustomerValueDTO(textValue.getValue());
                })
                .orElseThrow(() -> new EntityNotFoundException("Text value not found"));
    }

    private CustomerValueDTO updateLongValue(CustomerAttributes customerAttribute, CustomerValueDTO customerValueDTO) {
        return customerLongValueJpaRepository.findByCustomerAttributesId(customerAttribute.getId())
                .map(longValue -> {
                    longValue.setValue(Long.parseLong(customerValueDTO.getValue()));
                    return new CustomerValueDTO(String.valueOf(longValue.getValue()));
                })
                .orElseThrow(() -> new EntityNotFoundException("Long value not found"));
    }

    private CustomerValueDTO updateDateValue(CustomerAttributes customerAttribute, CustomerValueDTO customerValueDTO) {
        return customerDateValueJpaRepository.findByCustomerAttributesId(customerAttribute.getId())
                .map(dateValue -> {
                    dateValue.setValue(parseStringToDate(customerValueDTO.getValue()));
                    return new CustomerValueDTO(dateValue.getValue().toString());
                })
                .orElseThrow(() -> new EntityNotFoundException("Date value not found"));
    }

    private Date parseStringToDate(String value) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        try {
            return dateFormat.parse(value);
        } catch (ParseException e) {
            logger.error("Invalid value");
            throw new IllegalArgumentException("Invalid date format");
        }
    }
}