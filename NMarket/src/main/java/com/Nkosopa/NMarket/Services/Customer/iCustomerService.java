package com.Nkosopa.NMarket.Services.Customer;

import com.Nkosopa.NMarket.DTO.Customer.CustomerAttributeDTO;
import com.Nkosopa.NMarket.DTO.Customer.CustomerDTO;
import com.Nkosopa.NMarket.DTO.Customer.CustomerValueDTO;
import com.Nkosopa.NMarket.DTO.Product.ProductDTO;
import jakarta.transaction.Transactional;

import java.util.List;
import java.util.Optional;

public interface iCustomerService {
    void newCustomer(CustomerDTO customerDTO);

    Optional<CustomerDTO> findCustomerById(Long customerId);

    void deleteUser(Long customerId);

//    @Transactional
//    void updateCustomerProfile(CustomerDTO customerDTO, List<CustomerValueDTO> valueDTOList);

    CustomerDTO updateCustomerProfile(CustomerDTO customerDTO);

    //view
    List<CustomerDTO> getAllCustomer();

}
