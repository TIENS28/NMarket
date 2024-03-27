package com.Nkosopa.NMarket.Controller.Other;

import com.Nkosopa.NMarket.Services.Other.Impl.ShoppingCartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/cart")
public class ShoppingCartController {

    @Autowired
    private ShoppingCartService shoppingCartService;

    @PostMapping("/addProduct")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_CUSTOMER')")
    public void addProductToCart(@RequestParam("productId") Long productId, @RequestParam("cartId") Long cartId) {
        shoppingCartService.addProductToCart(productId, cartId);
    }

    @DeleteMapping("/removeProduct")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_CUSTOMER')")
    public void removeProductFromShoppingCart(@RequestParam("productId") long productId, @RequestParam("cartId") long cartId) {
        shoppingCartService.removeProductFromShoppingCart(productId, cartId);
    }

    @DeleteMapping("/cancel")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_CUSTOMER')")
    public void cancelShoppingCart(@RequestParam("cartId") long cartId) {
        shoppingCartService.cancelShoppingCart(cartId);
    }
}
