package com.Nkosopa.NMarket.Repository.Customer.JPA;

import com.Nkosopa.NMarket.Entity.Customer.CustomerLongValue;
import com.Nkosopa.NMarket.Repository.Customer.CustomerLongValueRepository;
import com.Nkosopa.NMarket.Repository.Product.JPA.CommonValueRepository;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CustomerLongValueJpaRepository extends CustomerCommonValueRepository<CustomerLongValue, Long> {
    Optional<CustomerLongValue> findByCustomerAttributesId(Long id);
}
