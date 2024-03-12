package com.Nkosopa.NMarket.Repository.Customer.JPA;

import com.Nkosopa.NMarket.Entity.Customer.CustomerTextValue;
import com.Nkosopa.NMarket.Repository.Customer.CustomerTextValueRepository;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CustomerTextValueJpaRepository extends JpaRepository<CustomerTextValue, Long>, CustomerTextValueRepository {

    @Transactional
    @Modifying
    @Query(value = "DELETE tv, lv, dv, ca "
            + "FROM customer_entity_text tv "
            + "LEFT JOIN customer_entity_int lv ON tv.customer_id = lv.customer_id "
            + "LEFT JOIN customer_attribute ca ON tv.customer_id = ca.customer_id "
            + "LEFT JOIN customer_date_value dv ON tv.customer_id = dv.customer_id "
            + "WHERE tv.customer_id = :customerId", nativeQuery = true)
    void deleteValueByCustomerId(@Param("customerId") Long customerId);


    @Transactional
    @Modifying
    @Query(value = "DELETE tv, lv, dv"
            + "FROM customer_entity_text tv "
            + "LEFT JOIN customer_attribute ca ON tv.customer_id = ca.customer_id "
            + "LEFT JOIN customer_entity_int lv ON tv.customer_id = lv.customer_id "
            + "LEFT JOIN customer_date_value dv ON tv.customer_id = dv.customer_id "
            + "WHERE tv.customer_attribute_id = :customerId", nativeQuery = true)
    void deleteAttributeValueByCustomerId(@Param("customerId") Long customerId);
}

































































