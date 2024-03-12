package com.Nkosopa.NMarket.Repository.Customer.JPA;

import com.Nkosopa.NMarket.Entity.Customer.CustomerTextValue;
import com.Nkosopa.NMarket.Repository.Customer.CustomerTextValueRepository;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerTextValueJpaRepository extends JpaRepository<CustomerTextValue, Long>, CustomerTextValueRepository {


}
