package com.Nkosopa.NMarket.Repository.Customer.JPA;

import com.Nkosopa.NMarket.Entity.Customer.CustomerTextValue;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface CustomerTextValueJpaRepository extends CustomerCommonValueRepository<CustomerTextValue, Long> {


    @Query("SELECT tv from CustomerTextValue tv " +
            "Left join CustomerAttributeEAV ae ON ae.id = tv.attribute.id " +
            "where tv.attribute.id = :id")
    Optional<CustomerTextValue> findByCustomerAttributesId(Long id);

    @Modifying
    @Transactional
    @Query("Delete from CustomerTextValue tv " +
            "where tv.customer.id = :id ")
    void deleteByCustomerId(Long id);

    @Modifying
    @Transactional
    @Query("Delete from CustomerTextValue tv " +
            "where tv.customer.id = :customerId and tv.attribute.id = :attributeId")
     void deleteByCustomerIdAndAttributeId(Long customerId, Long attributeId);
}

































































