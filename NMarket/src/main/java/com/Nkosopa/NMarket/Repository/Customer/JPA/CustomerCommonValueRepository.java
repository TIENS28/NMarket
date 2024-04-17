package com.Nkosopa.NMarket.Repository.Customer.JPA;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

import java.util.List;

@NoRepositoryBean
public interface CustomerCommonValueRepository<T, ID> extends JpaRepository<T, Long> {

    List<T> findByCustomerId(Long customerId);


    List<T> findByCustomerIdAndAttributeId(Long productId, Long attributeId);

}
