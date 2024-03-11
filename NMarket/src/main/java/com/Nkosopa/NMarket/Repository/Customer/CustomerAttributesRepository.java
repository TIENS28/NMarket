package com.Nkosopa.NMarket.Repository.Customer;

import com.Nkosopa.NMarket.DTO.Customer.CustomerAttributeDTO;
import com.Nkosopa.NMarket.Entity.Customer.CustomerAttributes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CustomerAttributesRepository extends JpaRepository<CustomerAttributes, Long>{
    List<CustomerAttributeDTO> getCustomerAttributes(Long customerId);
}
