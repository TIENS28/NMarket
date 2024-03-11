package com.Nkosopa.NMarket.Services.Customer;

import com.Nkosopa.NMarket.DTO.Customer.CustomerAttributeDTO;
import com.Nkosopa.NMarket.DTO.Customer.CustomerDTO;
import com.Nkosopa.NMarket.DTO.Customer.CustomerValueDTO;

public interface iCustomerService {
    void newCustomer(CustomerDTO customerDTO);

    void addAttribute(CustomerAttributeDTO attributeDTO);

    void addValueToCustomerAttribute(Long customerAttributeId, CustomerValueDTO valueDTO);
}
