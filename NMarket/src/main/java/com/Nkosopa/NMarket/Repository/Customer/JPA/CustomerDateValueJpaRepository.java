package com.Nkosopa.NMarket.Repository.Customer.JPA;

import com.Nkosopa.NMarket.Entity.Customer.CustomerDateValue;
import com.Nkosopa.NMarket.Repository.Customer.CustomerDateTimeRepository;
import com.Nkosopa.NMarket.Repository.Product.JPA.CommonValueRepository;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CustomerDateValueJpaRepository extends CustomerCommonValueRepository<CustomerDateValue, Long> {

    Optional<CustomerDateValue> findByCustomerAttributesId(Long id);
}
