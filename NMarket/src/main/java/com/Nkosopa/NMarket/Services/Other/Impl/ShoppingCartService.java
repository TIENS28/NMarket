package com.Nkosopa.NMarket.Services.Other.Impl;

import com.Nkosopa.NMarket.Converter.Other.CartConverter;
import com.Nkosopa.NMarket.DTO.Other.CartProductDTO;
import com.Nkosopa.NMarket.DTO.Other.ShoppingCartDTO;
import com.Nkosopa.NMarket.Entity.Customer.Customer;
import com.Nkosopa.NMarket.Entity.Other.CartProduct;
import com.Nkosopa.NMarket.Entity.Other.ShoppingCart;
import com.Nkosopa.NMarket.Entity.Product.Product;
import com.Nkosopa.NMarket.Repository.Customer.JPA.ShoppingCartJpaRepository;
import com.Nkosopa.NMarket.Repository.Other.JPA.ProductCartJpaRepository;
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
    private AuthenticationService authenticationService;
    @Autowired
    private ProductCartJpaRepository productCartJpaRepository;

    @Override
    public ShoppingCartDTO addProductsToCart(List<CartProductDTO> cartProductDTOList) {
        Customer customer = authenticationService.getCurrentCustomer();

        ShoppingCart cart;

        Optional<ShoppingCart> existingCartOptional = shoppingCartJpaRepository.findByCustomerId(customer.getId());

        if (existingCartOptional.isPresent()) {
            cart = existingCartOptional.get();
        } else {
            cart = new ShoppingCart();
            cart.setCustomer(customer);
            cart.setTotalPrice(0.0);
            cart.setCartProductsList(new ArrayList<>());
            shoppingCartJpaRepository.save(cart);
        }

        for (CartProductDTO cartProductDTO : cartProductDTOList) {
            Long productId = cartProductDTO.getProductDTO().getId();
            Long quantityToAdd = cartProductDTO.getQuantity();

            Optional<Product> productOptional = productJpaRepository.findById(productId);

            if (productOptional.isPresent()) {
                Product product = productOptional.get();

                CartProduct existingCartProduct = cart.getCartProductsList().stream()
                        .filter(cp -> cp.getProduct().getId().equals(productId))
                        .findFirst()
                        .orElse(null);

                if (existingCartProduct != null) {
                    existingCartProduct.setQuantity(existingCartProduct.getQuantity() + quantityToAdd);
                    cart.setTotalPrice(cart.getTotalPrice() + (product.getPrice() * quantityToAdd));
                } else {
                    CartProduct newCartProduct = new CartProduct();
                    newCartProduct.setProduct(product);
                    newCartProduct.setQuantity(quantityToAdd);
                    newCartProduct.setCart(cart);
                    productCartJpaRepository.save(newCartProduct);
                    cart.getCartProductsList().add(newCartProduct);
                    cart.setTotalPrice(cart.getTotalPrice() + (product.getPrice() * quantityToAdd));
                }
            } else {
                throw new EntityNotFoundException("Product with ID " + productId + " not found");
            }
        }

        shoppingCartJpaRepository.save(cart);

        return cartConverter.mapEntityToDTO(cart);
    }



    @Override
    public ShoppingCartDTO removeProductFromShoppingCart(List<Long> productIds, Long cartId) {
        Optional<ShoppingCart> cartOptional = shoppingCartJpaRepository.findById(cartId);
        if (cartOptional.isPresent()) {
            ShoppingCart cart = cartOptional.get();
            for (Long productId : productIds) {
                Optional<CartProduct> productOptional = productCartJpaRepository.findByProductId(productId);

                if (productOptional.isPresent()) {
                    CartProduct cartProduct = productOptional.get();
                    if (cart.getCartProductsList().contains(cartProduct)) {
                        cart.getCartProductsList().remove(cartProduct);
                        double priceReduction = cartProduct.getProduct().getPrice() * cartProduct.getQuantity();
                        cart.setTotalPrice(cart.getTotalPrice() - priceReduction);
                    } else {
                        throw new RuntimeException("Product not found in the shopping cart");
                    }
                }
            }
            double roundedTotalPrice = Math.round(cart.getTotalPrice() * 100.0) / 100.0;
            if (Math.abs(roundedTotalPrice) < 0.01) {
                cart.setTotalPrice(0.0);
            }
            shoppingCartJpaRepository.save(cart);
            return cartConverter.mapEntityToDTO(cart);
        } else {
            throw new RuntimeException("Shopping cart not found");
        }
    }


    @Override
    public ShoppingCartDTO cancelShoppingCart(Long cartId) {
        Optional<ShoppingCart> cartOptional = shoppingCartJpaRepository.findById(cartId);

        if (cartOptional.isPresent()) {
            ShoppingCart cart = cartOptional.get();
            cart.getProductList().clear();
            cart.setTotalPrice(0.0);

            shoppingCartJpaRepository.delete(cart);
            return cartConverter.mapEntityToDTO(cart);
        } else {
            throw new RuntimeException("Shopping cart not found");
        }
    }

}