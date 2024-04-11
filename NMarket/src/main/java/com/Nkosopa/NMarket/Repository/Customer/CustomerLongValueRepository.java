package com.Nkosopa.NMarket.Repository.Customer;

import org.springframework.data.jpa.repository.JpaRepository;

import com.Nkosopa.NMarket.Entity.Customer.CustomerLongValue;
import org.springframework.data.jpa.repository.Query;

public interface CustomerLongValueRepository extends JpaRepository<CustomerLongValue, Long>{

}
