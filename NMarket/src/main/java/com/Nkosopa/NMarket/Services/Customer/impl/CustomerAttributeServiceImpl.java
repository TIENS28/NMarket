package com.Nkosopa.NMarket.Services.Customer.impl;

import com.Nkosopa.NMarket.Converter.Customer.CustomerAttributeConverter;
import com.Nkosopa.NMarket.DTO.Customer.CustomerAttributeDTO;
import com.Nkosopa.NMarket.Entity.Customer.Customer;
import com.Nkosopa.NMarket.Entity.Customer.CustomerAttributes;
import com.Nkosopa.NMarket.Repository.Customer.JPA.*;
import com.Nkosopa.NMarket.Services.Customer.iCustomerAttributeService;
import com.Nkosopa.NMarket.Services.Other.Impl.AuthenticationService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CustomerAttributeServiceImpl implements iCustomerAttributeService {

    @Autowired
    private CustomerAttributeJpaRepository customerAttributeJpaRepository;

    @Autowired
    private CustomerTextValueJpaRepository customerTextValueJpaRepository;

    @Autowired
    private CustomerLongValueJpaRepository customerLongValueJpaRepository;

    @Autowired
    private CustomerDateValueJpaRepository customerDateValueJpaRepository;

    @Autowired
    private CustomerJPARepository customerJPARepository;

    @Autowired
    private CustomerAttributeConverter customerAttributeConverter;

    @Autowired
    private AuthenticationService authenticationService;


    @Override
    public List<CustomerAttributeDTO> addAttributeToOneCustomer(Long customerId, List<CustomerAttributeDTO> attributeDTOs) {
        Optional<Customer> customerOptional = customerJPARepository.findById(customerId);

        if (customerOptional.isEmpty()) {
            throw new EntityNotFoundException("Customer not found with ID: " + customerId);
        }

        Customer customer = customerOptional.get();

        customerAttributeConverter.convertCustomerAttributeDTOtoEntity(attributeDTOs, customer);

        return attributeDTOs;
    }//add attribute to one customer

    @Override
    public List<CustomerAttributeDTO> addAttributesToAllCustomers(List<CustomerAttributeDTO> attributeDTOs) {
        List<Customer> customers = customerJPARepository.findAll();
        for (Customer customer : customers) {
            customerAttributeConverter.convertCustomerAttributeDTOtoEntity(attributeDTOs, customer);
        }

        return attributeDTOs;
    }//add attribute to all customers


    @Override
    public void deleteSingleCustomerAttribute(Long customerId, List<String> attributeCodes){
        List<CustomerAttributes> customerAttributes = customerAttributeJpaRepository.findByCustomerIdAndAttributeCode(customerId, attributeCodes);
        customerAttributes.forEach(this::deleteAssociatedValues);
        customerAttributeJpaRepository.deleteCustomerAttributeByIdAndAttributeCode(customerId, attributeCodes);
    }

    @Override
    public void deleteAttributesOfAllCustomer(List<String> attributeCodes) {
        List<CustomerAttributes> customerAttributes = customerAttributeJpaRepository.findByAttributeCode(attributeCodes);
        customerAttributes.forEach(this::deleteAssociatedValues);
        customerAttributeJpaRepository.deleteCustomerAttributeByAttributeCode(attributeCodes);
    }

    private void deleteAssociatedValues(CustomerAttributes customerAttribute) {
        customerTextValueJpaRepository.deleteAll(customerAttribute.getTextValues());
        customerLongValueJpaRepository.deleteAll(customerAttribute.getIntValues());
        customerDateValueJpaRepository.deleteAll(customerAttribute.getDateValues());
    }//delete value for each customerAttributes

}
