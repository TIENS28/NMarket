package com.Nkosopa.NMarket.Repository.Product.JPA;

import com.Nkosopa.NMarket.Entity.Product.ProductAttributes;
import com.Nkosopa.NMarket.Repository.Product.ProductAttributesRepository;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ProductAttributeJpaRepository extends JpaRepository<ProductAttributes, Long>, ProductAttributesRepository {

    @Query("SELECT pa " +
            "FROM ProductAttributes pa " +
            "WHERE pa.product.id = :productId " +
            "AND pa.attribute_code IN :attributeCodes")
    List<ProductAttributes> findByProductIdAndAttributeCode(Long productId, List<String> attributeCodes);

    @Transactional
    @Modifying
    @Query(value = "DELETE " +
            "FROM product_attribute " +
            "WHERE product_id = :productId " +
            "AND attribute_code IN :attributeCodes", nativeQuery = true)
    void deleteProductAttributeByIdAndAttributeCode(Long productId, List<String> attributeCodes);
}
