package com.Nkosopa.NMarket.Services.Other.Impl;

import com.Nkosopa.NMarket.Converter.Other.OrderConverter;
import com.Nkosopa.NMarket.DTO.Other.OrderDTO;
import com.Nkosopa.NMarket.Entity.Customer.Customer;
import com.Nkosopa.NMarket.Entity.Other.OrderList;
import com.Nkosopa.NMarket.Entity.Other.ShoppingCart;
import com.Nkosopa.NMarket.Entity.Product.Product;
import com.Nkosopa.NMarket.Repository.Customer.JPA.ShoppingCartJpaRepository;
import com.Nkosopa.NMarket.Repository.Other.JPA.OrderJpaRepository;
import com.Nkosopa.NMarket.Repository.Product.JPA.ProductJpaRepository;
import com.Nkosopa.NMarket.Services.Other.iOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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
    public OrderDTO confirmOrder(long cartId) {
        Optional<ShoppingCart> cartOptional = shoppingCartJpaRepository.findById(cartId);


        if (cartOptional.isPresent()) {
            ShoppingCart cart = cartOptional.get();

            OrderList order = new OrderList();

            
            order.setCustomer(cart.getCustomer());

            List<Product> productList = new ArrayList<>(cart.getProductList());
            order.setProductList(productList);

            order.setTotalPrice(cart.getTotalPrice());

            for (Product product : productList) {
                product.setStock(product.getStock() - 1);
                productJpaRepository.save(product);
            }
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
            for(Product product : order.getProductList()){
                product.setStock(product.getStock() + 1);
            }
            orderJpaRepository.save(order);

            return orderConverter.mapEntityToDTO(order);
        } else {
            throw new RuntimeException("Order not found");
        }
    }


}