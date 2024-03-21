package com.Nkosopa.NMarket.Repository.Customer.JPA;

import com.Nkosopa.NMarket.Entity.Customer.CustomerDateValue;
import com.Nkosopa.NMarket.Repository.Customer.CustomerDateTimeRepository;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CustomerDateValueJpaRepository extends JpaRepository<CustomerDateValue, Long>, CustomerDateTimeRepository {

    Optional<CustomerDateValue> findByCustomerAttributesId(Long id);
}
