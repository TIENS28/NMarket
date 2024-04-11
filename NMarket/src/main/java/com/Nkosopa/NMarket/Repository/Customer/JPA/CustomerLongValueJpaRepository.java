package com.Nkosopa.NMarket.Repository.Customer.JPA;

import com.Nkosopa.NMarket.Entity.Customer.CustomerLongValue;
import com.Nkosopa.NMarket.Repository.Customer.CustomerLongValueRepository;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CustomerLongValueJpaRepository extends JpaRepository<CustomerLongValue, Long>, CustomerLongValueRepository {
    Optional<CustomerLongValue> findByCustomerAttributesId(Long id);
}
