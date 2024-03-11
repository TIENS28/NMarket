package com.Nkosopa.NMarket.Repository.Customer;

import org.springframework.data.jpa.repository.JpaRepository;

import com.Nkosopa.NMarket.Entity.Customer.CustomerDateValue;

public interface CustomerDateTimeRepository extends JpaRepository<CustomerDateValue, Long>{

}
