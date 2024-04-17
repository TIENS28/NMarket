package com.Nkosopa.NMarket.Services.Customer.impl;

import com.Nkosopa.NMarket.DTO.Customer.CustomerDateValueDTO;
import com.Nkosopa.NMarket.DTO.Customer.CustomerLongValueDTO;
import com.Nkosopa.NMarket.DTO.Customer.CustomerTextValueDTO;
import com.Nkosopa.NMarket.Entity.Customer.CustomerDateValue;
import com.Nkosopa.NMarket.Entity.Customer.CustomerLongValue;
import com.Nkosopa.NMarket.Entity.Customer.CustomerTextValue;
import com.Nkosopa.NMarket.Repository.Customer.JPA.*;
import com.Nkosopa.NMarket.Services.Customer.iCustomerValueService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class CustomerValueServiceImpl implements iCustomerValueService {

    @Autowired
    private CustomerTextValueJpaRepository customerTextValueJpaRepository;

    @Autowired
    private CustomerLongValueJpaRepository customerLongValueJpaRepository;

    @Autowired
    private CustomerDateValueJpaRepository customerDateValueJpaRepository;

    private static final Logger logger = LoggerFactory.getLogger(CustomerServiceImpl.class);

    @Autowired
    private CustomerJPARepository customerJPARepository;

    @Autowired
    private CustomerAttributeEAVJPARepository customerAttributeEAVJPARepository;

//    @Override
//    public CustomerValueDTO addValueToCustomerAttribute(Long customerAttributeId, CustomerValueDTO valueDTO) {
//        CustomerAttributeEAV customerAttribute = customerAttributeEAVJPARepository.findById(customerAttributeId)
//                .orElseThrow(() -> new EntityNotFoundException("Attribute not found"));
//
//        DataType dataType = customerAttribute.getDataType();
//
//        switch (dataType) {
//            case STRING:
//                CustomerTextValue textValue = new CustomerTextValue();
//                textValue.setValue(valueDTO.getValue());
//                textValue.setAttribute(customerAttribute);
//                textValue.setCustomer(valueDTO.getCustomer());
//                customerTextValueJpaRepository.save(textValue);
//                return new CustomerValueDTO(textValue.getValue());
//            case LONG:
//                CustomerLongValue longValue = new CustomerLongValue();
//                longValue.setValue(Long.parseLong(valueDTO.getValue()));
//                longValue.setCustomerAttributes(customerAttribute);
//                longValue.setCustomer(customerAttribute.getCustomer());
//                customerLongValueJpaRepository.save(longValue);
//                return new CustomerValueDTO(String.valueOf(longValue.getValue()));
//            case DATE:
//                CustomerDateValue dateValue = new CustomerDateValue();
//                dateValue.setValue(parseStringToDate(valueDTO.getValue()));
//                dateValue.setCustomerAttributes(customerAttribute);
//                dateValue.setCustomer(customerAttribute.getCustomer());
//                customerDateValueJpaRepository.save(dateValue);
//                return new CustomerValueDTO(dateValue.getValue().toString());
//            default:
//                throw new IllegalArgumentException("Unsupported data type");
//        }
//    }
//
//    @Override
//    public List<CustomerValueDTO> addValuesToCustomerAttributes(List<CustomerValueDTO> valueDTOs) {
//        List<CustomerValueDTO> savedValues = new ArrayList<>();
//        for (CustomerValueDTO valueDTO : valueDTOs) {
//            savedValues.add(addValueToCustomerAttribute(valueDTO.getAttributeId(), valueDTO));
//        }
//        return savedValues;
//    }


    public void updateTextValues(List<CustomerTextValueDTO> textValues) {
        for (CustomerTextValueDTO textValueDTO : textValues) {
            String textValueContent = textValueDTO.getValue();
            if (textValueDTO.getAttributeId()!=null) {

                Optional<CustomerTextValue> existingValueOptional = customerTextValueJpaRepository.findByCustomerAttributesId(textValueDTO.getAttributeId());

                if (existingValueOptional.isPresent()) {
                    CustomerTextValue existingValue = existingValueOptional.get();

                    if (existingValue.getCustomer().getId().equals(textValueDTO.getCustomerId()) &&
                            existingValue.getAttribute().getId().equals(textValueDTO.getAttributeId())) {

                        existingValue.setValue(textValueContent);
                        customerTextValueJpaRepository.save(existingValue);
                    } else {
                        throw new IllegalArgumentException("Invalid existing text value for customerId and attributeId");
                    }
                } else {
                    CustomerTextValue newValue = new CustomerTextValue();
                    newValue.setCustomer(customerJPARepository.getReferenceById(textValueDTO.getCustomerId()));
                    newValue.setAttribute(customerAttributeEAVJPARepository.getReferenceById(textValueDTO.getAttributeId()));
                    newValue.setValue(textValueContent);
                    customerTextValueJpaRepository.save(newValue);
                }
            }
        }
    }


    public void updateLongValues(List<CustomerLongValueDTO> longValueDTOS) {
        for (CustomerLongValueDTO longValueDTO : longValueDTOS) {
            Long longValueContent = longValueDTO.getValue();
            if (longValueDTO.getAttributeId()!=null) {

                Optional<CustomerLongValue> existingValueOptional = customerLongValueJpaRepository.findByCustomerAttributesId(longValueDTO.getAttributeId());

                if (existingValueOptional.isPresent()) {
                    CustomerLongValue existingValue = existingValueOptional.get();

                    if (existingValue.getCustomer().getId().equals(longValueDTO.getCustomerId()) &&
                            existingValue.getAttribute().getId().equals(longValueDTO.getAttributeId())) {

                        existingValue.setValue(longValueContent);
                        customerLongValueJpaRepository.save(existingValue);
                    } else {
                        throw new IllegalArgumentException("Invalid existing text value for customerId and attributeId");
                    }
                } else {
                    CustomerLongValue longValue = new CustomerLongValue();
                    longValue.setCustomer(customerJPARepository.getReferenceById(longValueDTO.getCustomerId()));
                    longValue.setAttribute(customerAttributeEAVJPARepository.getReferenceById(longValueDTO.getAttributeId()));
                    longValue.setValue(longValueContent);
                    customerLongValueJpaRepository.save(longValue);
                }
            }
        }
    }


    public void updateDateValues(List<CustomerDateValueDTO> dateValues) {
        for (CustomerDateValueDTO dateValueDTO : dateValues) {
            Date dateValueContent = dateValueDTO.getValue();
            if (dateValueDTO.getAttributeId()!=null) {

                Optional<CustomerDateValue> existingValueOptional = customerDateValueJpaRepository.findByCustomerAttributesId(dateValueDTO.getAttributeId());

                if (existingValueOptional.isPresent()) {
                    CustomerDateValue existingValue = existingValueOptional.get();

                    if (existingValue.getCustomer().getId().equals(dateValueDTO.getCustomerId()) &&
                            existingValue.getAttribute().getId().equals(dateValueDTO.getAttributeId())) {

                        existingValue.setValue(dateValueContent);
                        customerDateValueJpaRepository.save(existingValue);
                    } else {
                        throw new IllegalArgumentException("Invalid existing text value for customerId and attributeId");
                    }
                } else {
                    CustomerDateValue dateValue = new CustomerDateValue();
                    dateValue.setCustomer(customerJPARepository.getReferenceById(dateValueDTO.getCustomerId()));
                    dateValue.setAttribute(customerAttributeEAVJPARepository.getReferenceById(dateValueDTO.getAttributeId()));
                    dateValue.setValue(dateValueContent);
                    customerDateValueJpaRepository.save(dateValue);
                }
            }
        }
    }


    public Date parseStringToDate(String value) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        try {
            return dateFormat.parse(value);
        } catch (ParseException e) {
            logger.error("invalid value");
            return null;
        }
    }

    public void deleteCustomerValue(Long customerId) {
        customerTextValueJpaRepository.findByCustomerId(customerId).clear();
        customerLongValueJpaRepository.findByCustomerId(customerId).clear();
        customerDateValueJpaRepository.findByCustomerId(customerId).clear();
    }

    public void deleteCustomerAttributeValue(Long customerId, Long attributeId) {
        customerTextValueJpaRepository.deleteByCustomerIdAndAttributeId(customerId, attributeId);
        customerLongValueJpaRepository.deleteByCustomerIdAndAttributeId(customerId, attributeId);
        customerDateValueJpaRepository.deleteByCustomerIdAndAttributeId(customerId, attributeId);
    }
}