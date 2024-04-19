package com.Nkosopa.NMarket.Controller.Other;

import com.Nkosopa.NMarket.DTO.Other.CartProductDTO;
import com.Nkosopa.NMarket.DTO.Other.ShoppingCartDTO;
import com.Nkosopa.NMarket.Services.Other.Impl.ShoppingCartService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api/v1/cart", consumes = MediaType.APPLICATION_JSON_VALUE)
public class ShoppingCartController {

    @Autowired
    private ShoppingCartService shoppingCartService;

    @PostMapping("/addProducts")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_CUSTOMER')")
    public ResponseEntity<ShoppingCartDTO> addProductsToCart(@RequestBody List<CartProductDTO> cartProductDTOList) {
        try {
            ShoppingCartDTO cartDTO = shoppingCartService.addProductsToCart(cartProductDTOList);
            return ResponseEntity.status(HttpStatus.CREATED).body(cartDTO);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }


    @DeleteMapping("/removeProduct")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_CUSTOMER')")
    public ResponseEntity<ShoppingCartDTO> removeProductFromShoppingCart(@RequestBody List<Long> productIds, @RequestParam Long cartId) {
        ShoppingCartDTO cartDTO = shoppingCartService.removeProductFromShoppingCart(productIds, cartId);
        return ResponseEntity.ok(cartDTO);
    }

    @DeleteMapping("/cancel")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_CUSTOMER')")
    public ResponseEntity<ShoppingCartDTO> cancelShoppingCart(@RequestParam("cartId") long cartId) {
        ShoppingCartDTO cartDTO = shoppingCartService.cancelShoppingCart(cartId);
        return ResponseEntity.ok(cartDTO);
    }

}
