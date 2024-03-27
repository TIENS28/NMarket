package com.Nkosopa.NMarket.Services.Other.Impl;

import com.Nkosopa.NMarket.Entity.Other.ShoppingCart;
import com.Nkosopa.NMarket.Entity.Product.Product;
import com.Nkosopa.NMarket.Repository.Customer.JPA.ShoppingCartJpaRepository;
import com.Nkosopa.NMarket.Repository.Product.JPA.ProductJpaRepository;
import com.Nkosopa.NMarket.Services.Other.iShoppingCartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ShoppingCartService implements iShoppingCartService {

    @Autowired
    private ProductJpaRepository productJpaRepository;

    @Autowired
    private ShoppingCartJpaRepository shoppingCartJpaRepository;


    @Override
    public void addProductToCart(Long productId, Long cartId) {
        Optional<Product> productOptional = productJpaRepository.findById(productId);
        Optional<ShoppingCart> cartOptional = shoppingCartJpaRepository.findById(cartId);

        if (productOptional.isPresent() && cartOptional.isPresent()) {
            Product product = productOptional.get();
            ShoppingCart cart = cartOptional.get();

            // Check if the product is in stock
            if (product.getStock() > 0) {
                // Decrease the product stock
                product.setStock(product.getStock() - 1);
                productJpaRepository.save(product);

                // Add the product to the shopping cart
                cart.getProductList().add(product);
                cart.setTotalPrice(cart.getTotalPrice() + product.getPrice());
                shoppingCartJpaRepository.save(cart);
            } else {
                throw new RuntimeException("Product out of stock");
            }
        } else {
            throw new RuntimeException("Product or cart not found");
        }
    }


    @Override
    public void removeProductFromShoppingCart(long productId, long cartId) {
        Optional<Product> productOptional = productJpaRepository.findById(productId);
        Optional<ShoppingCart> cartOptional = shoppingCartJpaRepository.findById(cartId);

        if (productOptional.isPresent() && cartOptional.isPresent()) {
            Product product = productOptional.get();
            ShoppingCart cart = cartOptional.get();

            // Check if the product is in the shopping cart
            if (cart.getProductList().contains(product)) {

                // Remove the product from the shopping cart
                cart.getProductList().remove(product);

                // Update the total price of the shopping cart
                cart.setTotalPrice(cart.getTotalPrice() - product.getPrice());

                shoppingCartJpaRepository.save(cart);
            } else {
                throw new RuntimeException("Product not found in the shopping cart");
            }
        } else {
            throw new RuntimeException("Product or shopping cart not found");
        }
    }

    @Override
    public void cancelShoppingCart(long cartId) {
        Optional<ShoppingCart> cartOptional = shoppingCartJpaRepository.findById(cartId);

        if (cartOptional.isPresent()) {
            ShoppingCart cart = cartOptional.get();

            // Remove all products from the shopping cart
            cart.getProductList().clear();

            // Set total price to zero
            cart.setTotalPrice(0L);

            // Delete the shopping cart
            shoppingCartJpaRepository.delete(cart);
        } else {
            throw new RuntimeException("Shopping cart not found");
        }
    }

}