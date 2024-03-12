package com.Nkosopa.NMarket.Repository.Customer.JPA;

import com.Nkosopa.NMarket.DTO.Customer.CustomerAttributeDTO;
import com.Nkosopa.NMarket.Entity.Customer.CustomerAttributes;
import com.Nkosopa.NMarket.Repository.Customer.CustomerAttributesRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CustomerAttributeJpaRepository extends JpaRepository<CustomerAttributes, Long>, CustomerAttributesRepository {

    @Query("SELECT ca, COALESCE(tv.value, lv.value, dv.value) AS value " +
            "FROM CustomerAttributes ca " +
            "LEFT JOIN CustomerTextValue tv ON ca.id = tv.customerAttributes.id AND ca.customer.id = tv.customer.id " +
            "LEFT JOIN CustomerLongValue lv ON ca.id = lv.customerAttributes.id AND ca.customer.id = lv.customer.id " +
            "LEFT JOIN CustomerDateValue dv ON ca.id = dv.customerAttributes.id AND ca.customer.id = dv.customer.id " +
            "WHERE ca.customer.id = :customerId")
    List<CustomerAttributes> getCustomerAttributes(Long customerId);

    void deleteByCustomerId(Long customerId);
}
