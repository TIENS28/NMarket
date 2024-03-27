package com.Nkosopa.NMarket.Converter.Other;

import com.Nkosopa.NMarket.Converter.Customer.CustomerConverter;
import com.Nkosopa.NMarket.Converter.Product.ProductConverter;
import com.Nkosopa.NMarket.DTO.Other.OrderDTO;
import com.Nkosopa.NMarket.Entity.Other.OrderList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class OrderConverter {

    private CustomerConverter customerConverter;

    @Autowired
    private ProductConverter productConverter;

    public OrderDTO mapEntityToDTO(OrderList order) {
        OrderDTO orderDTO = new OrderDTO();
        orderDTO.setId(order.getId());
        orderDTO.setCustomerDTO(customerConverter.mapEntityToDTO(order.getCustomer()));
        orderDTO.setProductDTOS(productConverter.mapEntitiesToDTOs(order.getProductList()));
        orderDTO.setTotalPrice(order.getTotalPrice());
        return orderDTO;
    }
}
