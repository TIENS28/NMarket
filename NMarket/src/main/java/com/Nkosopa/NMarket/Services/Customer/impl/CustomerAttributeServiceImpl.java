package com.Nkosopa.NMarket.Services.Customer.impl;

import com.Nkosopa.NMarket.Converter.Customer.CustomerAttributeEAVConverter;
import com.Nkosopa.NMarket.Converter.Customer.CustomerConverter;
import com.Nkosopa.NMarket.DTO.Customer.CustomerAttributeEAVDTO;
import com.Nkosopa.NMarket.DTO.Customer.CustomerDTO;
import com.Nkosopa.NMarket.DTO.Product.AttributeDTO;
import com.Nkosopa.NMarket.Entity.Customer.Customer;
import com.Nkosopa.NMarket.Entity.Customer.CustomerAttributeEAV;
import com.Nkosopa.NMarket.Entity.Customer.CustomerAttributes;
import com.Nkosopa.NMarket.Entity.Product.AttributeEAV;
import com.Nkosopa.NMarket.Entity.Product.Product;
import com.Nkosopa.NMarket.Repository.Customer.JPA.*;
import com.Nkosopa.NMarket.Services.Customer.iCustomerAttributeService;
import com.Nkosopa.NMarket.Services.Other.Impl.AuthenticationService;
import jakarta.transaction.Transactional;
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
    private CustomerAttributeEAVConverter customerAttributeEAVConverter;
    @Autowired
    private CustomerAttributeEAVJPARepository customerAttributeEAVJPARepository;
    @Autowired
    private CustomerConverter customerConverter;

    @Override
    @Transactional
    public CustomerDTO addAttributesToCustomer(Long customerId, List<CustomerAttributeEAVDTO> attributeDTOList) {
        Customer customer = customerJPARepository.findById(customerId)
                .orElseThrow(() -> new IllegalArgumentException("Customer not found with id: " + customerId));

        List<String> existingAttributeNames = customer.getAttributeEAVList().stream()
                .map(CustomerAttributeEAV::getAttributeName)
                .toList();

        for (CustomerAttributeEAVDTO attributeDTO : attributeDTOList) {
            String attributeName = attributeDTO.getAttributeName();
            if (!existingAttributeNames.contains(attributeName)) {
                Optional<CustomerAttributeEAV> attributeEAVOptional = customerAttributeEAVJPARepository.findByAttributeName(attributeName);

                if (attributeEAVOptional.isPresent()) {
                    CustomerAttributeEAV attributeEAV = attributeEAVOptional.get();
                    customer.getAttributeEAVList().add(attributeEAV);
                } else {
                    CustomerAttributeEAV newAttributeEAV = new CustomerAttributeEAV();
                    newAttributeEAV.setAttributeName(attributeName);
                    newAttributeEAV.setDataType(attributeDTO.getDataType());
                    customerAttributeEAVJPARepository.save(newAttributeEAV);
                    customer.getAttributeEAVList().add(newAttributeEAV);
                }
            }
        }
        customerJPARepository.save(customer);
        return customerConverter.mapEntityToDTO(customer);
    }

//
//    @Transactional
//    public void addAttributeToAllCustomer(CustomerAttributeEAVDTO attributeDTO) {
//        List<Customer> customers = customerJPARepository.findAll();
//        CustomerAttributeEAV attributeEAV = customerAttributeEAVConverter.mapToEntity(attributeDTO);
//        attributeEAV = customerAttributeEAVJPARepository.save(attributeEAV);
//        for (Customer customer : customers) {
//            customer.getAttributeEAVList().add(attributeEAV);
//        }
//        customerJPARepository.saveAll(customers);
//    }


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
