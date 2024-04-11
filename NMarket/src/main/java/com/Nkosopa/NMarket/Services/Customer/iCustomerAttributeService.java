package com.Nkosopa.NMarket.Services.Customer;

import com.Nkosopa.NMarket.DTO.Customer.CustomerAttributeDTO;

import java.util.List;

public interface iCustomerAttributeService {


    List<CustomerAttributeDTO> addAttributeToOneCustomer(Long customerId, List<CustomerAttributeDTO> attributeDTOs)//add attribute to one customer
    ;

    List<CustomerAttributeDTO> addAttributesToAllCustomers(List<CustomerAttributeDTO> attributeDTOs)//add attribute to all customers
    ;

    void deleteSingleCustomerAttribute(Long customerId, List<String> attributeCodes);

    void deleteAttributesOfAllCustomer(List<String> attributeCodes);
}
