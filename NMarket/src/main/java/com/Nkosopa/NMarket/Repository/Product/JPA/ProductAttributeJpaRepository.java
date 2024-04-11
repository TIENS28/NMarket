package com.Nkosopa.NMarket.Repository.Product.JPA;

import com.Nkosopa.NMarket.DTO.Product.ProductAttributesDTO;
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

    @Query("SELECT pa " +
            "FROM ProductAttributes pa " +
            "WHERE pa.attribute_code IN :attributeCodes")
    List<ProductAttributes> findByAttributeCode(List<String> attributeCodes);

    @Transactional
    @Modifying
    @Query(value = "DELETE " +
            "FROM product_attribute " +
            "WHERE attribute_code IN :attributeCodes", nativeQuery = true)
    void deleteProductAttributeByAttributeCode(List<String> attributeCodes);

    @Query("SELECT pa, COALESCE(tv.value, lv.value, dv.value) AS value " +
            "FROM ProductAttributes pa " +
            "LEFT JOIN ProductTextValue tv ON pa.id = tv.productAttributes.id AND pa.product.id = tv.productAttributes.id " +
            "LEFT JOIN ProductLongValue lv ON pa.id = lv.productAttributes.id AND pa.product.id = lv.product.id " +
            "LEFT JOIN ProductDateValue dv ON pa.id = dv.productAttributes.id AND pa.product.id = dv.product.id " +
            "WHERE pa.product.id = :productId")
    List<ProductAttributes> getProductAtribute(Long productId);


    @Query("SELECT pa " +
            "FROM ProductAttributes pa " +
            "INNER JOIN Product p ON p.id = pa.product.id " +
            "WHERE p.id = :id")
    List<ProductAttributesDTO> findByProductId(Long id);
}
