package com.Nkosopa.NMarket.Repository.Other.JPA;

import com.Nkosopa.NMarket.Entity.Other.CartProduct;
import com.Nkosopa.NMarket.Repository.Other.ProductCartRepository;
import com.Nkosopa.NMarket.Repository.Product.JPA.ProductJpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface ProductCartJpaRepository extends ProductCartRepository {

    @Query(value = "Delete from CartProduct cp " +
            "where cp.cart.id = :cartId")
    void deleteByCartId(Long cartId);

    @Query(value = "select cp from CartProduct cp " +
            "where cp.product.id = :productId")
    Optional<CartProduct> findByProductId(Long productId);
}
