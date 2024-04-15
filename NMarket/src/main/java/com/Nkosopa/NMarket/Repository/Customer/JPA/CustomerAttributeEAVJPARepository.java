package com.Nkosopa.NMarket.Repository.Customer.JPA;

import com.Nkosopa.NMarket.Entity.Customer.CustomerAttributeEAV;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface CustomerAttributeEAVJPARepository extends JpaRepository<CustomerAttributeEAV, Long> {

    @Query("SELECT ae FROM CustomerAttributeEAV ae WHERE ae.attributeName = :attributeName")
    Optional<CustomerAttributeEAV> findByAttributeName(String attributeName);
}
