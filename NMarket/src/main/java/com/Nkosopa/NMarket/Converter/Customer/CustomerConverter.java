package com.Nkosopa.NMarket.Converter.Customer;

import com.Nkosopa.NMarket.DTO.Customer.*;
import com.Nkosopa.NMarket.Entity.Customer.*;
import com.Nkosopa.NMarket.Repository.Customer.JPA.CustomerAttributeJpaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class CustomerConverter {

    @Autowired
    private CustomerAttributeConverter customerAttributeConverter;

    public CustomerDTO mapEntityToDTO(Customer customer) {
        CustomerDTO customerDTO = new CustomerDTO();

        if (customer != null && customer.getId() != null){
            customerDTO.setId(customer.getId());
            customerDTO.setFirstName(customer.getFirstName());
            customerDTO.setLastName(customer.getLastName());
            customerDTO.setEmail(customer.getEmail());
            customerDTO.setRole(customer.getRole().name());
            customerDTO.setAttributesDTO(customerAttributeConverter.mapAttributesToDTOs(customer.getAttributes()));
        }

        return customerDTO;
    }
}
