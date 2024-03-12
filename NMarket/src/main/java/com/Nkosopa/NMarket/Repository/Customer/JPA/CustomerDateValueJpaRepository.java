package com.Nkosopa.NMarket.Repository.Customer.JPA;

import com.Nkosopa.NMarket.Entity.Customer.CustomerDateValue;
import com.Nkosopa.NMarket.Repository.Customer.CustomerDateTimeRepository;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerDateValueJpaRepository extends JpaRepository<CustomerDateValue, Long>, CustomerDateTimeRepository {

}
