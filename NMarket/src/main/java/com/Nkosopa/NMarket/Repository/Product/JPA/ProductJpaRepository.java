package com.Nkosopa.NMarket.Repository.Product.JPA;

import com.Nkosopa.NMarket.Entity.Product.Product;
import com.Nkosopa.NMarket.Repository.Product.ProductRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProductJpaRepository extends JpaRepository<Product, Long>, ProductRepository {


    @Query(value = "SELECT DISTINCT p " +
            "FROM Product p " +
            "LEFT JOIN ProductType pt ON pt.id = p.productType.id " +
            "LEFT JOIN ProductAttributes pa ON pa.product.id = p.id " +
            "LEFT JOIN ProductLongValue lv ON lv.productAttributes.id = pa.id " +
            "LEFT JOIN ProductTextValue tv ON tv.productAttributes.id = pa.id " +
            "LEFT JOIN ProductDateValue dv ON dv.productAttributes.id = pa.id " +
            "WHERE p.name LIKE %:query% OR p.productType.pType LIKE %:query%",
            countQuery = "SELECT COUNT(DISTINCT p.id) " +
                    "FROM Product p " +
                    "LEFT JOIN ProductType pt ON pt.id = p.productType.id " +
                    "LEFT JOIN ProductAttributes pa ON pa.product.id = p.id " +
                    "LEFT JOIN ProductLongValue lv ON lv.productAttributes.id = pa.id " +
                    "LEFT JOIN ProductTextValue tv ON tv.productAttributes.id = pa.id " +
                    "LEFT JOIN ProductDateValue dv ON dv.productAttributes.id = pa.id " +
                    "WHERE p.name LIKE %:query% OR p.productType.pType LIKE %:query%")
    public Page<Product> searchProducts(@Param("query") String query, Pageable pageable);

    @Query(value = "SELECT DISTINCT p " +
            "FROM Product p " +
            "LEFT JOIN ProductType pt ON pt.id = p.productType.id " +
            "LEFT JOIN ProductAttributes pa ON pa.product.id = p.id " +
            "LEFT JOIN ProductLongValue lv ON lv.productAttributes.id = pa.id " +
            "LEFT JOIN ProductTextValue tv ON tv.productAttributes.id = pa.id " +
            "LEFT JOIN ProductDateValue dv ON dv.productAttributes.id = pa.id ",
            countQuery = "SELECT COUNT(DISTINCT p.id) FROM Product p")
    public Page<Product> findAllProduct(Pageable pageable);
}
