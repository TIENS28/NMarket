package com.Nkosopa.NMarket.Repository.Customer;

import com.Nkosopa.NMarket.Entity.Customer.CustomerAttributes;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerAttributeJpaRepository extends JpaRepository<CustomerAttributes, Long>, CustomerAttributesRepository {
}
