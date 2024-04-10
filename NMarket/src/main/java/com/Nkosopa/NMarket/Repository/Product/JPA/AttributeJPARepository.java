package com.Nkosopa.NMarket.Repository.Product.JPA;

import com.Nkosopa.NMarket.Entity.Product.AttributeEAV;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AttributeJPARepository extends JpaRepository<AttributeEAV, Long> {
}
