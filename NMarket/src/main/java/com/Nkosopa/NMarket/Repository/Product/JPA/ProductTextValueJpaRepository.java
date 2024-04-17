package com.Nkosopa.NMarket.Repository.Product.JPA;

import com.Nkosopa.NMarket.Entity.Product.ProductTextValue;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface ProductTextValueJpaRepository extends CommonValueRepository<ProductTextValue, Long>{


    @Query("SELECT tv from ProductTextValue tv " +
            "Left join AttributeEAV ae ON ae.id = tv.attribute.id " +
            "where tv.attribute.id = :id")
    Optional<ProductTextValue> findByProductAttributesId(Long id);

    ProductTextValue findByAttributeId(Long attributeId);

    @Modifying
    @Transactional
    @Query("Delete from ProductTextValue tv " +
            "where tv.product.id = :productId and tv.attribute.id = :attributeId")
    void deleteByProductIdAndAttributeId(Long productId, Long attributeId);
}
