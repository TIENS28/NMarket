package com.Nkosopa.NMarket.Repository.Other.JPA;

import com.Nkosopa.NMarket.Repository.Other.ProductCartRepository;
import com.Nkosopa.NMarket.Repository.Product.JPA.ProductJpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ProductCartJpaRepository extends ProductCartRepository {

    @Query(value = "Delete from CartProduct cp " +
            "where cp.cart.id = :cartId")
    void deleteByCartId(Long cartId);
}
