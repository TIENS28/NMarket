package com.Nkosopa.NMarket.Services.Other.Impl;

import com.Nkosopa.NMarket.Converter.Other.OrderConverter;
import com.Nkosopa.NMarket.DTO.Other.OrderDTO;
import com.Nkosopa.NMarket.Entity.Other.OrderList;
import com.Nkosopa.NMarket.Entity.Other.ShoppingCart;
import com.Nkosopa.NMarket.Repository.Customer.JPA.ShoppingCartJpaRepository;
import com.Nkosopa.NMarket.Repository.Other.JPA.OrderJpaRepository;
import com.Nkosopa.NMarket.Services.Other.iOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class OrderServiceImpl implements iOrderService {

    @Autowired
    private OrderJpaRepository orderJpaRepository;

    @Autowired
    private ShoppingCartJpaRepository shoppingCartJpaRepository;

    @Autowired
    private OrderConverter orderConverter;

    @Override
    public OrderDTO confirmOrder(long cartId) {
        Optional<ShoppingCart> cartOptional = shoppingCartJpaRepository.findById(cartId);
        if (cartOptional.isPresent()) {
            ShoppingCart cart = cartOptional.get();

            OrderList order = new OrderList();
            order.setCustomer(cart.getCustomer());
            order.setProductList(cart.getProductList());
            order.setTotalPrice(cart.getTotalPrice());

            orderJpaRepository.save(order);

            shoppingCartJpaRepository.delete(cart);

            return orderConverter.mapEntityToDTO(order);
        } else {
            throw new RuntimeException("Shopping cart not found");
        }
    }

    @Override
    public OrderDTO cancelOrder(long orderId) {
        Optional<OrderList> orderOptional = orderJpaRepository.findById(orderId);
        if (orderOptional.isPresent()) {
            OrderList order = orderOptional.get();
            order.setStatus("CANCELED");
            orderJpaRepository.save(order);

            return orderConverter.mapEntityToDTO(order);
        } else {
            throw new RuntimeException("Order not found");
        }
    }
}