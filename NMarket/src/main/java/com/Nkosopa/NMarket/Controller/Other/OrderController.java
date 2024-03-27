package com.Nkosopa.NMarket.Controller.Other;

import com.Nkosopa.NMarket.DTO.Other.OrderDTO;
import com.Nkosopa.NMarket.Services.Other.Impl.OrderServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<OrderDTO> confirmOrder(@RequestParam("cartId") long cartId) {
        OrderDTO orderDTO = orderServiceImpl.confirmOrder(cartId);
        return ResponseEntity.status(HttpStatus.CREATED).body(orderDTO);
    }

    @PutMapping("/cancel")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_CUSTOMER')")
    public ResponseEntity<OrderDTO> cancelOrder(@RequestParam("orderId") long orderId) {
        OrderDTO orderDTO = orderServiceImpl.cancelOrder(orderId);
        return ResponseEntity.ok(orderDTO);
    }
}
