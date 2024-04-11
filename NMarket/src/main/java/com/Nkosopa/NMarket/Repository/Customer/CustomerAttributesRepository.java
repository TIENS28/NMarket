package com.Nkosopa.NMarket.Repository.Customer;

import com.Nkosopa.NMarket.Entity.Customer.CustomerAttributes;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerAttributesRepository extends JpaRepository<CustomerAttributes, Long>{
//    List<CustomerAttributeDTO> getCustomerAttributes(Long customerId);
}
