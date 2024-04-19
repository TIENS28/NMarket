package com.Nkosopa.NMarket.Services.Other.Impl;

import com.Nkosopa.NMarket.Converter.Other.OrderConverter;
import com.Nkosopa.NMarket.DTO.Other.OrderDTO;
import com.Nkosopa.NMarket.Entity.Other.CartProduct;
import com.Nkosopa.NMarket.Entity.Other.OrderList;
import com.Nkosopa.NMarket.Entity.Other.ShoppingCart;
import com.Nkosopa.NMarket.Entity.Product.Product;
import com.Nkosopa.NMarket.Repository.Customer.JPA.ShoppingCartJpaRepository;
import com.Nkosopa.NMarket.Repository.Other.JPA.OrderJpaRepository;
import com.Nkosopa.NMarket.Repository.Other.JPA.ProductCartJpaRepository;
import com.Nkosopa.NMarket.Repository.Product.JPA.ProductJpaRepository;
import com.Nkosopa.NMarket.Services.Other.iOrderService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class OrderServiceImpl implements iOrderService {

    @Autowired
    private OrderJpaRepository orderJpaRepository;

    @Autowired
    private ShoppingCartJpaRepository shoppingCartJpaRepository;

    @Autowired
    private OrderConverter orderConverter;

    @Autowired
    private ProductJpaRepository productJpaRepository;


    @Override
    @Transactional
    public OrderDTO placeOrder(long cartId) {
        Optional<ShoppingCart> cartOptional = shoppingCartJpaRepository.findById(cartId);

        if (cartOptional.isPresent()) {
            ShoppingCart cart = cartOptional.get();
            if (cart.getCartProductsList().isEmpty()) {
                return null;
            } else {
                OrderList order = new OrderList();
                order.setCustomer(cart.getCustomer());

                List<CartProduct> cartProductsList = new ArrayList<>(cart.getCartProductsList());
                List<Product> updatedProducts = new ArrayList<>();

                for (CartProduct cartProduct : cartProductsList) {
                    Product product = cartProduct.getProduct();
                    Long quantity = cartProduct.getQuantity();

                    if (product.getStock() < quantity) {
                        throw new RuntimeException("Product Id: " + product.getId() + " is out of stock");
                    }

                    product.setStock(product.getStock() - quantity);
                    updatedProducts.add(product);
                }
                productJpaRepository.saveAll(updatedProducts);

                order.setCartProductList(cartProductsList);
                order.setTotalPrice(cart.getTotalPrice());
                order.setOderDate(LocalDateTime.now());
                orderJpaRepository.save(order);

                OrderDTO orderDTO = new OrderDTO();
                orderDTO = orderConverter.mapEntityToDTO(order);

                cartProductsList.clear();
                shoppingCartJpaRepository.delete(cart);

                return orderDTO;
            }
        }else {
            throw new RuntimeException("Shopping cart not found");
        }
    }



    @Override
    public OrderDTO cancelOrder(long orderId) {
        Optional<OrderList> orderOptional = orderJpaRepository.findById(orderId);

        if (orderOptional.isPresent()) {
            OrderList order = orderOptional.get();
            List<CartProduct> cartProductsList = order.getCartProductList();

            for (CartProduct cartProduct : cartProductsList) {
                Product product = cartProduct.getProduct();
                Long quantity = cartProduct.getQuantity();

                product.setStock(product.getStock() + quantity);
                productJpaRepository.save(product);
            }
            orderJpaRepository.delete(order);
            return orderConverter.mapEntityToDTO(order);
        } else {
            throw new RuntimeException("Order not found");
        }
    }

}