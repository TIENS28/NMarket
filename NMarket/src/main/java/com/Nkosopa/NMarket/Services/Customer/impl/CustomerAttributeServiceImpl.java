package com.Nkosopa.NMarket.Services.Customer.impl;

import com.Nkosopa.NMarket.Converter.Customer.CustomerConverter;
import com.Nkosopa.NMarket.Entity.Customer.CustomerAttributes;
import com.Nkosopa.NMarket.Repository.Customer.JPA.*;
import com.Nkosopa.NMarket.Services.Customer.iCustomerAttributeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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
    private CustomerConverter customerConverter;

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



//    @Override
//    public List<CustomerAttributeDTO> findAttributesByCustomerId(Long customerId){
////        Customer customer = customerJPARepository.findById(customerId).orElse(null);
////
////        if (customer == null) {
////            return null;
////        }
//        List<CustomerAttributes> customerAttributes = customerAttributeJpaRepository.findByCustomerId(customerId);
//
//        return customerConverter.mapAttributesToDTOs(customerAttributes);
//    }

}
