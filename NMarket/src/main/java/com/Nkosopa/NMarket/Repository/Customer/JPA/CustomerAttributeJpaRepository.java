package com.Nkosopa.NMarket.Repository.Customer.JPA;

import com.Nkosopa.NMarket.DTO.Customer.CustomerAttributeDTO;
import com.Nkosopa.NMarket.DTO.Customer.CustomerDTO;
import com.Nkosopa.NMarket.Entity.Customer.Customer;
import com.Nkosopa.NMarket.Entity.Customer.CustomerAttributes;
import com.Nkosopa.NMarket.Repository.Customer.CustomerAttributesRepository;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface CustomerAttributeJpaRepository extends JpaRepository<CustomerAttributes, Long>, CustomerAttributesRepository {

    @Query("SELECT ca, COALESCE(tv.value, lv.value, dv.value) AS value " +
            "FROM CustomerAttributes ca " +
            "LEFT JOIN CustomerTextValue tv ON ca.id = tv.customerAttributes.id AND ca.customer.id = tv.customer.id " +
            "LEFT JOIN CustomerLongValue lv ON ca.id = lv.customerAttributes.id AND ca.customer.id = lv.customer.id " +
            "LEFT JOIN CustomerDateValue dv ON ca.id = dv.customerAttributes.id AND ca.customer.id = dv.customer.id " +
            "WHERE ca.customer.id = :customerId")
    List<CustomerAttributes> getCustomerAttributes(Long customerId);

    void deleteByCustomerId(Long customerId);

    @Query("SELECT ca " +
            "FROM CustomerAttributes ca " +
            "LEFT JOIN Customer  c ON ca.customer.id = c.id " +
            "WHERE ca.customer.id = :customerId")
    List<CustomerAttributes> findByCustomerId(Long customerId);


    @Query("SELECT ca " +
            "FROM CustomerAttributes ca " +
            "WHERE ca.customer.id = :customerId " +
            "AND ca.attribute_code IN :attributeCodes")
    List<CustomerAttributes> findByCustomerIdAndAttributeCode(Long customerId, List<String> attributeCodes);

    @Transactional
    @Modifying
    @Query(value = "DELETE " +
                    "FROM customer_attribute " +
                    "WHERE customer_id = :customerId " +
                    "AND attribute_code IN :attributeCodes", nativeQuery = true)
    void deleteCustomerAttributeByIdAndAttributeCode(@Param("customerId") Long customerId, @Param("attributeCodes") List<String> attributeCodes);

    @Transactional
    @Modifying
    @Query(value = "DELETE " +
            "FROM customer_attribute " +
            "WHERE attribute_code IN :attributeCodes", nativeQuery = true)
    void deleteCustomerAttributeByAttributeCode(@Param("attributeCodes") List<String> attributeCodes);

    @Query("SELECT ca " +
            "FROM CustomerAttributes ca " +
            "WHERE ca.attribute_code IN :attributeCodes")
    List<CustomerAttributes> findByAttributeCode(@Param("attributeCodes")List<String> attributeCodes);
}
