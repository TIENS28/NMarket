package com.Nkosopa.NMarket.Services.Customer;

import com.Nkosopa.NMarket.DTO.Customer.CustomerAttributeDTO;
import com.Nkosopa.NMarket.DTO.Customer.CustomerDTO;
import com.Nkosopa.NMarket.DTO.Customer.CustomerValueDTO;
import jakarta.transaction.Transactional;

import java.util.List;
import java.util.Optional;

public interface iCustomerService {
    void newCustomer(CustomerDTO customerDTO);


    void addValueToCustomerAttribute(Long customerAttributeId, CustomerValueDTO valueDTO);

    void addValuesToCustomerAttributes(List<CustomerValueDTO> valueDTOs)//add multiple value to multiple attributes
    ;

    Optional<CustomerDTO> findCustomerById(Long customerId);

    void deleteUser(Long customerId);

    @Transactional
    void updateCustomerProfile(CustomerDTO customerDTO, List<CustomerValueDTO> valueDTOList);

    //view
    List<CustomerDTO> findAll();

    CustomerDTO getOneCustomer(Long customerId);
}
