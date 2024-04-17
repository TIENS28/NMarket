package com.Nkosopa.NMarket.Repository.Customer.JPA;

import com.Nkosopa.NMarket.Entity.Customer.CustomerLongValue;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface CustomerLongValueJpaRepository extends CustomerCommonValueRepository<CustomerLongValue, Long> {

    @Query("SELECT lv from CustomerLongValue lv " +
            "Left join CustomerAttributeEAV ae ON ae.id = lv.attribute.id")
    Optional<CustomerLongValue> findByCustomerAttributesId(Long id);

    @Query("Delete from CustomerLongValue lv " +
            "where lv.customer.id = :id ")
    public void deleteByCustomerId(Long id);

    @Modifying
    @Transactional
    @Query("Delete from CustomerLongValue lv " +
            "where lv.customer.id = :customerId and lv.attribute.id = :attributeId")
    void deleteByCustomerIdAndAttributeId(Long customerId, Long attributeId);
}
