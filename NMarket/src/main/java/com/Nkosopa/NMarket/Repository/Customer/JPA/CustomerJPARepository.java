package com.Nkosopa.NMarket.Repository.Customer.JPA;

import com.Nkosopa.NMarket.Entity.Customer.Customer;
import com.Nkosopa.NMarket.Repository.Customer.CustomerRepository;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CustomerJPARepository extends JpaRepository<Customer, Long>, CustomerRepository {

    Customer findByVerificationToken(String token);

    boolean existsByEmail(String email);

    Optional<Customer> findByEmail(String email);
}
