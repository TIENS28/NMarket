package com.Nkosopa.NMarket.Services.Customer;

import com.Nkosopa.NMarket.DTO.Customer.CustomerValueDTO;

import java.util.List;

public interface iCustomerValueService {
    void addValueToCustomerAttribute(Long customerAttributeId, CustomerValueDTO valueDTO)//add value to one attribute
    ;

    void addValuesToCustomerAttributes(List<CustomerValueDTO> valueDTOs)//add multiple value to multiple attributes
    ;
}
