package com.Nkosopa.NMarket.Services.Customer.impl;

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

    private static final Logger logger = LoggerFactory.getLogger(CustomerServiceImpl.class);

    @Override
    public void addValueToCustomerAttribute(Long customerAttributeId, CustomerValueDTO valueDTO) {
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
                break;

            case LONG:
                CustomerLongValue longValue = new CustomerLongValue();
                longValue.setValue(Long.parseLong(valueDTO.getValue()));
                longValue.setCustomerAttributes(customerAttribute);
                longValue.setCustomer(customerAttribute.getCustomer());
                customerLongValueJpaRepository.save(longValue);
                break;

            case DATE:
                CustomerDateValue dateValue = new CustomerDateValue();
                dateValue.setValue(parseStringToDate(valueDTO.getValue()));
                dateValue.setCustomerAttributes(customerAttribute);
                dateValue.setCustomer(customerAttribute.getCustomer());
                customerDateValueJpaRepository.save(dateValue);
                break;

            default:

                throw new IllegalArgumentException("Unsupported data type");
        }
    }//add value to one attribute

    @Override
    public void addValuesToCustomerAttributes(List<CustomerValueDTO> valueDTOs) {
        for (CustomerValueDTO valueDTO : valueDTOs) {
            addValueToCustomerAttribute(valueDTO.getAttributeId(), valueDTO);
        }
    }//add multiple value to multiple attributes

    private Date parseStringToDate(String value) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        try {
            return dateFormat.parse(value);
        } catch (ParseException e) {
            logger.error("invalid value");
            return null;
        }
    }
}
