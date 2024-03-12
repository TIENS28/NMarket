package com.Nkosopa.NMarket.Services.Customer;

import com.Nkosopa.NMarket.DTO.Customer.CustomerAttributeDTO;

import java.util.List;

public interface iCustomerAttributeService {

    void deleteSingleCustomerAttribute(Long customerId, List<String> attributeCodes);

    void deleteAttributesOfAllCustomer(List<String> attributeCodes);
}
