package com.Nkosopa.NMarket.Converter.Other;

import com.Nkosopa.NMarket.Converter.Customer.CustomerConverter;
import com.Nkosopa.NMarket.Converter.Product.ProductConverter;
import com.Nkosopa.NMarket.DTO.Other.ShoppingCartDTO;
import com.Nkosopa.NMarket.Entity.Other.ShoppingCart;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class CartConverter {

    @Autowired
    private ProductConverter productConverter;
    @Autowired
    private CartProductConverter cartProductConverter;

    public ShoppingCartDTO mapEntityToDTO(ShoppingCart cart) {
        ShoppingCartDTO dto = new ShoppingCartDTO();
        dto.setId(cart.getId());
        dto.setTotalPrice(cart.getTotalPrice());
        dto.setCustomerId(cart.getCustomer().getId());
        dto.setProductDTOList(cartProductConverter.mapToDTOs(cart.getCartProductsList()));
        return dto;
    }

    public List<ShoppingCartDTO> mapToDTOs(List<ShoppingCart> carts) {
        List<ShoppingCartDTO> cartDTOS = new ArrayList<>();
        for (ShoppingCart cart : carts) {
            cartDTOS.add(mapEntityToDTO(cart));
        }
        return cartDTOS;
    }

}
