package com.Nkosopa.NMarket.Repository.Customer;

import com.Nkosopa.NMarket.Entity.Customer.CustomerDateValue;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerDateTimeRepository extends JpaRepository<CustomerDateValue, Long>{

}
