package com.Nkosopa.NMarket.Converter.Other;

import com.Nkosopa.NMarket.Converter.Product.ProductConverter;
import com.Nkosopa.NMarket.DTO.Other.OrderDTO;
import com.Nkosopa.NMarket.Entity.Other.OrderList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class OrderConverter {

    @Autowired
    private CartProductConverter cartProductConverter;

    public OrderDTO mapEntityToDTO(OrderList order) {
        OrderDTO orderDTO = new OrderDTO();
        orderDTO.setId(order.getId());
        orderDTO.setCartProductDTOS(cartProductConverter.mapToDTOs(order.getCartProductList()));
        orderDTO.setTotalPrice(order.getTotalPrice());
        orderDTO.setOrderDate(order.getOderDate());
        return orderDTO;
    }

    public List<OrderDTO> mapToDTOs(List<OrderList> orders) {
        List<OrderDTO> orderDTOs = new ArrayList<>();
        for (OrderList order : orders) {
            orderDTOs.add(mapEntityToDTO(order));
        }
        return orderDTOs;
    }
}
