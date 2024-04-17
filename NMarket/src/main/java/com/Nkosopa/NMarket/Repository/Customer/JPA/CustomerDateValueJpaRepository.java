package com.Nkosopa.NMarket.Repository.Customer.JPA;

import com.Nkosopa.NMarket.Entity.Customer.CustomerDateValue;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface CustomerDateValueJpaRepository extends CustomerCommonValueRepository<CustomerDateValue, Long> {

    @Query("SELECT dv from CustomerDateValue dv " +
            "Left join CustomerAttributeEAV ae ON ae.id = dv.attribute.id")
    Optional<CustomerDateValue> findByCustomerAttributesId(Long id);

    @Query("Delete from CustomerDateValue dv " +
            "where dv.customer.id = :id ")
    public void deleteByCustomerId(Long id);

    @Modifying
    @Transactional
    @Query("Delete from CustomerDateValue dv " +
            "where dv.customer.id = :customerId and dv.attribute.id = :attributeId")
    void deleteByCustomerIdAndAttributeId(Long customerId, Long attributeId);
}
