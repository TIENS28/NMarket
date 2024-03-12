package com.Nkosopa.NMarket.Services.Customer.impl;

import com.Nkosopa.NMarket.Converter.Customer.CustomerConverter;
import com.Nkosopa.NMarket.DTO.Customer.*;
import com.Nkosopa.NMarket.Entity.Customer.*;
import com.Nkosopa.NMarket.Entity.DataType;
import com.Nkosopa.NMarket.Repository.Customer.*;
import com.Nkosopa.NMarket.Repository.Customer.JPA.CustomerAttributeJpaRepository;
import com.Nkosopa.NMarket.Repository.Customer.JPA.CustomerDateValueJpaRepository;
import com.Nkosopa.NMarket.Repository.Customer.JPA.CustomerLongValueJpaRepository;
import com.Nkosopa.NMarket.Repository.Customer.JPA.CustomerTextValueJpaRepository;
import com.Nkosopa.NMarket.Services.Customer.iCustomerService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class CustomerServiceImpl implements iCustomerService {

    @Autowired
    private CustomerAttributeJpaRepository customerAttributeJpaRepository;

    @Autowired
    private CustomerTextValueJpaRepository customerTextValueRepository;

    @Autowired
    private CustomerLongValueJpaRepository customerLongValueRepository;

    @Autowired
    private CustomerDateValueJpaRepository customerDateTimeRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private CustomerConverter customerConverter;

    @Override
    public void newCustomer(CustomerDTO customerDTO) {
        Customer newCustomer = new Customer();
        newCustomer.setFirstName(customerDTO.getFirstName());
        newCustomer.setLastName(customerDTO.getLastName());
        newCustomer.setUserName(customerDTO.getUserName());
        newCustomer.setPassword(customerDTO.getPassword());
        newCustomer.setEmail(customerDTO.getEmail());
        newCustomer.setAttributes(new ArrayList<>());

        customerRepository.save(newCustomer);
    }

    @Override
    public void addAttribute(CustomerAttributeDTO attributeDTO) {
        CustomerAttributes attributes = new CustomerAttributes();
        attributes.setAttribute_name(attributeDTO.getAttribute_name());
        attributes.setAttribute_code(attributeDTO.getAttribute_code());

        Optional<Customer> customerOptional = customerRepository.findById(attributeDTO.getCustomer_id());

        if (customerOptional.isPresent()) {
            Customer customer = customerOptional.get();
            attributes.setCustomer(customer);
            attributes.setDataType(attributeDTO.getDataType());
            customerAttributeJpaRepository.save(attributes);
        } else {
            throw new EntityNotFoundException("Customer not found with ID: " + attributeDTO.getCustomer_id());
        }

        attributes.setDataType(attributeDTO.getDataType());
        customerAttributeJpaRepository.save(attributes);
    }

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
                customerTextValueRepository.save(textValue);
                break;

            case LONG:
                CustomerLongValue longValue = new CustomerLongValue();
                longValue.setValue(Long.parseLong(valueDTO.getValue()));
                longValue.setCustomerAttributes(customerAttribute);
                longValue.setCustomer(customerAttribute.getCustomer());
                customerLongValueRepository.save(longValue);
                break;

            case DATE:
                CustomerDateValue dateValue = new CustomerDateValue();
                dateValue.setValue(parseStringToDate(valueDTO.getValue()));
                dateValue.setCustomerAttributes(customerAttribute);
                dateValue.setCustomer(customerAttribute.getCustomer());
                customerDateTimeRepository.save(dateValue);
                break;

            default:

                throw new IllegalArgumentException("Unsupported data type");
        }
    }


    private Date parseStringToDate(String value) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        try {
            return dateFormat.parse(value);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public Optional<CustomerDTO> findCustomerById(Long customerId) {
        Optional<Customer> customerOptional = customerRepository.findById(customerId);

        return customerOptional.map(customer -> {
            List<CustomerAttributes> customerAttributesList = customerAttributeJpaRepository.getCustomerAttributes(customerId);
            List<CustomerAttributeDTO> attributeDTOs = customerConverter.mapAttributesToDTOs(customerAttributesList);

            return CustomerDTO.builder()
                    .firstName(customer.getFirstName())
                    .lastName(customer.getLastName())
                    .userName(customer.getUserName())
                    .password(customer.getPassword())
                    .email(customer.getEmail())
                    .attributesDTO(attributeDTOs)
                    .build();
        });
    }

    @Override
    public void deleteUser(Long customerId){
//        customerTextValueRepository.deleteByCustomerId(customerId);
//        customerLongValueRepository.deleteByCustomerId(customerId);
//        customerDateTimeRepository.deleteByCustomerId(customerId);
        customerAttributeJpaRepository.deleteByCustomerId(customerId);
        customerRepository.deleteById(customerId);
    }
}
