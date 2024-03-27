package com.Nkosopa.NMarket.Controller.Other;

import com.Nkosopa.NMarket.DTO.Other.ShoppingCartDTO;
import com.Nkosopa.NMarket.Services.Other.Impl.ShoppingCartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<ShoppingCartDTO> addProductToCart(@RequestParam("productId") Long productId, @RequestParam("cartId") Long cartId) {
        ShoppingCartDTO cartDTO = shoppingCartService.addProductToCart(productId, cartId);
        return ResponseEntity.status(HttpStatus.CREATED).body(cartDTO);
    }

    @DeleteMapping("/removeProduct")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_CUSTOMER')")
    public ResponseEntity<ShoppingCartDTO> removeProductFromShoppingCart(@RequestParam("productId") long productId, @RequestParam("cartId") long cartId) {
        ShoppingCartDTO cartDTO = shoppingCartService.removeProductFromShoppingCart(productId, cartId);
        return ResponseEntity.ok(cartDTO);
    }

    @DeleteMapping("/cancel")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_CUSTOMER')")
    public ResponseEntity<ShoppingCartDTO> cancelShoppingCart(@RequestParam("cartId") long cartId) {
        ShoppingCartDTO cartDTO = shoppingCartService.cancelShoppingCart(cartId);
        return ResponseEntity.ok(cartDTO);
    }
}
