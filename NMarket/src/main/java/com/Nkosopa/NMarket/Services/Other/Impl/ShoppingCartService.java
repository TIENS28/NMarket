package com.Nkosopa.NMarket.Services.Other.Impl;

import com.Nkosopa.NMarket.Converter.Customer.CustomerConverter;
import com.Nkosopa.NMarket.Converter.Other.CartConverter;
import com.Nkosopa.NMarket.DTO.Other.ShoppingCartDTO;
import com.Nkosopa.NMarket.Entity.Customer.Customer;
import com.Nkosopa.NMarket.Entity.Other.ShoppingCart;
import com.Nkosopa.NMarket.Entity.Product.Product;
import com.Nkosopa.NMarket.Repository.Customer.JPA.CustomerJPARepository;
import com.Nkosopa.NMarket.Repository.Customer.JPA.ShoppingCartJpaRepository;
import com.Nkosopa.NMarket.Repository.Product.JPA.ProductJpaRepository;
import com.Nkosopa.NMarket.Services.Other.iShoppingCartService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ShoppingCartService implements iShoppingCartService {

    @Autowired
    private ProductJpaRepository productJpaRepository;

    @Autowired
    private ShoppingCartJpaRepository shoppingCartJpaRepository;

    @Autowired
    private CartConverter cartConverter;

    @Autowired
    private CustomerJPARepository customerJPARepository;

    @Autowired
    private AuthenticationService authenticationService;

    @Override
    public ShoppingCartDTO addProductsToCart(Long customerId, List<Long> productIds) {


        Customer customer = authenticationService.getCurrentCustomer();

        ShoppingCart cart;

        Optional<ShoppingCart> existingCartOptional = shoppingCartJpaRepository.findByCustomerId(customerId);

        if (existingCartOptional.isPresent()) {
            cart = existingCartOptional.get();
        } else {
            cart = new ShoppingCart();
            cart.setCustomer(customer);
            cart.setTotalPrice(0L);
            shoppingCartJpaRepository.save(cart);
        }
        List<Product> productList = new ArrayList<>();
        for (Long productId : productIds) {
            Optional<Product> productOptional = productJpaRepository.findById(productId);

            if (productOptional.isPresent()) {
                Product product = productOptional.get();

                if (product.getStock() > 0) {
                    productList.add(product);
                    cart.setProductList(productList);
                    cart.setTotalPrice(cart.getTotalPrice() + product.getPrice());
                } else {
                    throw new RuntimeException("Product with ID " + productId + " is out of stock");
                }
            } else {
                throw new EntityNotFoundException("Product with ID " + productId + " not found");
            }
        }
        shoppingCartJpaRepository.save(cart);
        return cartConverter.mapEntityToDTO(cart);

    }

    @Override
    public ShoppingCartDTO removeProductFromShoppingCart(Long productId, Long cartId) {
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
    public ShoppingCartDTO cancelShoppingCart(Long cartId) {
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