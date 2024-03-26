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
}