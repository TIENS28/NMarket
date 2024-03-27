package com.Nkosopa.NMarket.Services.Other;

import com.Nkosopa.NMarket.Entity.Customer.Customer;
import com.Nkosopa.NMarket.Entity.Product.Product;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public interface iShoppingCartService {

    @Autowired
    void addProductToCart(Long productId, Long cartId);

    void removeProductFromShoppingCart(long productId, long cartId);

    void cancelShoppingCart(long cartId);
}
