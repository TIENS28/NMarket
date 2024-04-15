package com.Nkosopa.NMarket.Services.Customer;

import com.Nkosopa.NMarket.DTO.Customer.CustomerAttributeDTO;
import com.Nkosopa.NMarket.DTO.Customer.CustomerAttributeEAVDTO;
import com.Nkosopa.NMarket.DTO.Customer.CustomerDTO;
import jakarta.transaction.Transactional;

import java.util.List;

public interface iCustomerAttributeService {


//    List<CustomerAttributeEAVDTO> addAttributesToAllCustomers(List<CustomerAttributeEAVDTO> attributeDTOs)//add attribute to all customers
//    ;

    @Transactional
    CustomerDTO addAttributesToCustomer(Long customerId, List<CustomerAttributeEAVDTO> attributeDTOList);

    void deleteSingleCustomerAttribute(Long customerId, List<String> attributeCodes);

    void deleteAttributesOfAllCustomer(List<String> attributeCodes);
}
