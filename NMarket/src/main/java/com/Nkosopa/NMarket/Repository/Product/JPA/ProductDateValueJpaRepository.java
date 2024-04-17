package com.Nkosopa.NMarket.Repository.Product.JPA;

import com.Nkosopa.NMarket.Entity.Product.ProductDateValue;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;


public interface ProductDateValueJpaRepository extends CommonValueRepository<ProductDateValue, Long> {

    @Query("SELECT dv from ProductDateValue dv " +
            "Left join AttributeEAV ae ON ae.id = dv.attribute.id " +
            "where dv.attribute.id = :id")
    Optional<ProductDateValue> findByProductAttributesId(Long id);

    @Modifying
    @Transactional
    @Query("Delete from ProductDateValue dv " +
            "where dv.product.id = :productId and dv.attribute.id = :attributeId")
    void deleteByProductIdAndAttributeId(Long productId, Long attributeId);
}
