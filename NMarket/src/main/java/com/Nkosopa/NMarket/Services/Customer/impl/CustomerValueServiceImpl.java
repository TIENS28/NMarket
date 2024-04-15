package com.Nkosopa.NMarket.Services.Customer.impl;

import com.Nkosopa.NMarket.DTO.Customer.CustomerDateValueDTO;
import com.Nkosopa.NMarket.DTO.Customer.CustomerLongValueDTO;
import com.Nkosopa.NMarket.DTO.Customer.CustomerTextValueDTO;
import com.Nkosopa.NMarket.DTO.Product.ProductLongValueDTO;
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


    public void updateTextValues(Long customerId, Long attributeId, List<CustomerTextValueDTO> textValues) {
        for (CustomerTextValueDTO textValueDTO : textValues) {
            String textValueContent = textValueDTO.getValue();
            if (textValueDTO.getId()!=null) {
                Long textValueId = textValueDTO.getId();

                Optional<CustomerTextValue> existingValueOptional = customerTextValueJpaRepository.findById(textValueId);
                if (existingValueOptional.isPresent()) {
                    CustomerTextValue existingValue = existingValueOptional.get();

                    if (existingValue.getCustomer().getId().equals(customerId) &&
                            existingValue.getAttribute().getId().equals(attributeId)) {

                        existingValue.setValue(textValueContent);
                        customerTextValueJpaRepository.save(existingValue);
                    } else {
                        throw new IllegalArgumentException("Invalid existing text value for customerId and attributeId");
                    }
                }
            } else {
                CustomerTextValue newValue = new CustomerTextValue();
                newValue.setCustomer(customerJPARepository.getReferenceById(customerId));
                newValue.setAttribute(customerAttributeEAVJPARepository.getReferenceById(attributeId));
                newValue.setValue(textValueContent);
                customerTextValueJpaRepository.save(newValue);
            }
        }
    }


    public void updateLongValues(Long customerId, Long attributeId, List<CustomerLongValueDTO> longValues) {
        for (CustomerLongValueDTO longValueDTO : longValues) {
            Long longValueContent = longValueDTO.getValue();

            if(longValueDTO.getId()!=null) {
                Long longValueId = longValueDTO.getId();

                Optional<CustomerLongValue> existingValueOptional = customerLongValueJpaRepository.findById(longValueId);
                if (existingValueOptional.isPresent()) {
                    CustomerLongValue existingValue = existingValueOptional.get();

                    if (existingValue.getCustomer().getId().equals(customerId) &&
                            existingValue.getAttribute().getId().equals(attributeId)) {

                        existingValue.setValue(longValueContent);
                        customerLongValueJpaRepository.save(existingValue);
                    } else {
                        throw new IllegalArgumentException("Invalid existing long value for customerId and attributeId");
                    }
                }
            }else{
                CustomerLongValue newValue = new CustomerLongValue();
                newValue.setCustomer(customerJPARepository.getReferenceById(customerId));
                newValue.setAttribute(customerAttributeEAVJPARepository.getReferenceById(attributeId));
                newValue.setValue(longValueContent);
                customerLongValueJpaRepository.save(newValue);
            }
        }
    }


    public void updateDateValues(Long customerId, Long attributeId, List<CustomerDateValueDTO> dateValues) {
        for (CustomerDateValueDTO dateValueDTO : dateValues) {
            Date dateValueContent = dateValueDTO.getValue();
            if (dateValueDTO.getId()!=null) {
                Long dateValueId = dateValueDTO.getId();

                Optional<CustomerDateValue> existingValueOptional = customerDateValueJpaRepository.findById(dateValueId);
                if (existingValueOptional.isPresent()) {
                    CustomerDateValue existingValue = existingValueOptional.get();

                    if (existingValue.getCustomer().getId().equals(customerId) &&
                            existingValue.getAttribute().getId().equals(attributeId)) {
                        existingValue.setValue(dateValueContent);
                        customerDateValueJpaRepository.save(existingValue);
                    } else {
                        throw new IllegalArgumentException("Invalid existing date value for customerId and attributeId");
                    }
                }
            }else {
                CustomerDateValue newValue = new CustomerDateValue();
                newValue.setCustomer(customerJPARepository.getReferenceById(customerId));
                newValue.setAttribute(customerAttributeEAVJPARepository.getReferenceById(attributeId));
                newValue.setValue(dateValueContent);
                customerDateValueJpaRepository.save(newValue);
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
}