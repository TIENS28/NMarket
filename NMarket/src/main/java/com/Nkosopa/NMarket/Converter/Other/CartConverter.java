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
        dto.setCustomerDTO(customerConverter.mapEntityToDTO(cart.getCustomer()));
        dto.setProductDTOS(productConverter.mapEntitiesToDTOs(cart.getProductList()));
        return dto;
    }

//    public ShoppingCart mapDTOToEntity(ShoppingCartDTO dto) {
//        ShoppingCart cart = new ShoppingCart();
//        cart.setId(dto.getId());
//        cart.setTotalPrice(dto.getTotalPrice());
//        cart.setCustomer(dto.getCustomerDTO());
//        cart.setProductList(dto.getProductDTOS());
//        return cart;
//    } //use later
}
