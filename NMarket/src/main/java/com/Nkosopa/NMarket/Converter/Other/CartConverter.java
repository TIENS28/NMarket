package com.Nkosopa.NMarket.Converter.Other;

import com.Nkosopa.NMarket.Converter.Customer.CustomerConverter;
import com.Nkosopa.NMarket.Converter.Product.ProductConverter;
import com.Nkosopa.NMarket.DTO.Other.ShoppingCartDTO;
import com.Nkosopa.NMarket.Entity.Other.ShoppingCart;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CartConverter {

    @Autowired
    private CustomerConverter customerConverter;

    @Autowired
    private ProductConverter productConverter;

    public ShoppingCartDTO mapEntityToDTO(ShoppingCart cart) {
        ShoppingCartDTO dto = new ShoppingCartDTO();
        dto.setId(cart.getId());
        dto.setTotalPrice(cart.getTotalPrice());
        dto.setCustomerId(cart.getCustomer().getId());
        dto.setProductDTOS(productConverter.mapEntitiesToDTOs(cart.getProductList()));
        return dto;
    }

}
