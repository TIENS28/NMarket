package com.Nkosopa.NMarket.Repository.Product.JPA;

import com.Nkosopa.NMarket.Entity.Product.AttributeEAV;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface AttributeJPARepository extends JpaRepository<AttributeEAV, Long> {

    @Query("SELECT ae FROM AttributeEAV ae WHERE ae.name = :attributeName")
    Optional<AttributeEAV> findByName(String attributeName);
}
