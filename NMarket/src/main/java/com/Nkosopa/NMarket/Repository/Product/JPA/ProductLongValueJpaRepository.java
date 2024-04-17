package com.Nkosopa.NMarket.Repository.Product.JPA;

import com.Nkosopa.NMarket.Entity.Product.ProductLongValue;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface ProductLongValueJpaRepository extends CommonValueRepository<ProductLongValue, Long> {

    @Query("SELECT lv from ProductLongValue lv " +
            "Left join AttributeEAV ae ON ae.id = lv.attribute.id " +
            "where lv.attribute.id = :id")
    Optional<ProductLongValue> findByProductAttributesId(Long id);
    
    @Modifying
    @Transactional
    @Query("Delete from ProductLongValue lv " +
            "where lv.product.id = :productId and lv.attribute.id = :attributeId")
    void deleteByProductIdAndAttributeId(Long productId, Long attributeId);

}
