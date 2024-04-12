package com.Nkosopa.NMarket.Repository.Product.JPA;

import com.Nkosopa.NMarket.Entity.Product.AttributeEAV;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AttributeJPARepository extends JpaRepository<AttributeEAV, Long> {

    Optional<AttributeEAV> findByName(String attributeName);
}
