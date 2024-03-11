package com.Nkosopa.NMarket.Repository.Customer;

import org.springframework.data.jpa.repository.JpaRepository;

import com.Nkosopa.NMarket.Entity.Customer.Customer;

public interface CustomerRepository extends JpaRepository<Customer, Long>{

}
