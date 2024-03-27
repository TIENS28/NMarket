package com.Nkosopa.NMarket.Services.Other.Impl;

import com.Nkosopa.NMarket.Converter.Other.CartConverter;
import com.Nkosopa.NMarket.DTO.Other.ShoppingCartDTO;
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

    @Autowired
    private CartConverter cartConverter;

    @Override
    public ShoppingCartDTO addProductToCart(Long productId, Long cartId) {
        Optional<Product> productOptional = productJpaRepository.findById(productId);
        Optional<ShoppingCart> cartOptional = shoppingCartJpaRepository.findById(cartId);

        if (productOptional.isPresent() && cartOptional.isPresent()) {
            Product product = productOptional.get();
            ShoppingCart cart = cartOptional.get();

            if (product.getStock() > 0) {
                product.setStock(product.getStock() - 1);
                productJpaRepository.save(product);

                cart.getProductList().add(product);
                cart.setTotalPrice(cart.getTotalPrice() + product.getPrice());
                shoppingCartJpaRepository.save(cart);
            } else {
                throw new RuntimeException("Product out of stock");
            }
            return cartConverter.mapEntityToDTO(cart);
        } else {
            throw new RuntimeException("Product or cart not found");
        }
    }



    @Override
    public ShoppingCartDTO removeProductFromShoppingCart(long productId, long cartId) {
        Optional<Product> productOptional = productJpaRepository.findById(productId);
        Optional<ShoppingCart> cartOptional = shoppingCartJpaRepository.findById(cartId);

        if (productOptional.isPresent() && cartOptional.isPresent()) {
            Product product = productOptional.get();
            ShoppingCart cart = cartOptional.get();

            if (cart.getProductList().contains(product)) {
                cart.getProductList().remove(product);
                cart.setTotalPrice(cart.getTotalPrice() - product.getPrice());

                shoppingCartJpaRepository.save(cart);
                return cartConverter.mapEntityToDTO(cart);
            } else {
                throw new RuntimeException("Product not found in the shopping cart");
            }
        } else {
            throw new RuntimeException("Product or shopping cart not found");
        }
    }

    @Override
    public ShoppingCartDTO cancelShoppingCart(long cartId) {
        Optional<ShoppingCart> cartOptional = shoppingCartJpaRepository.findById(cartId);

        if (cartOptional.isPresent()) {
            ShoppingCart cart = cartOptional.get();
            cart.getProductList().clear();
            cart.setTotalPrice(0L);

            shoppingCartJpaRepository.delete(cart);
            return cartConverter.mapEntityToDTO(cart);
        } else {
            throw new RuntimeException("Shopping cart not found");
        }
    }

}