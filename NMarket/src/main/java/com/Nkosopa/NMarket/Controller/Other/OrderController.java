package com.Nkosopa.NMarket.Controller.Other;

import com.Nkosopa.NMarket.Services.Other.Impl.OrderServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/order")
public class OrderController {

    @Autowired
    private OrderServiceImpl orderServiceImpl;

    @PostMapping("/confirm")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_CUSTOMER')")
    public void confirmOrder(@RequestParam("cartId") long cartId) {
        orderServiceImpl.confirmOrder(cartId);
    }

    @PutMapping("/cancel")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_CUSTOMER')")
    public void cancelOrder(@RequestParam("orderId") long orderId) {
        orderServiceImpl.cancelOrder(orderId);
    }
}
