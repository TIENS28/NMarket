package com.Nkosopa.NMarket.Converter.Other;

import com.Nkosopa.NMarket.Converter.Product.ProductConverter;
import com.Nkosopa.NMarket.DTO.Other.CartProductDTO;
import com.Nkosopa.NMarket.DTO.Other.ShoppingCartDTO;
import com.Nkosopa.NMarket.Entity.Other.CartProduct;
import com.Nkosopa.NMarket.Entity.Other.ShoppingCart;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class CartProductConverter {

    @Autowired
    private ProductConverter productConverter;

    public CartProductConverter(ProductConverter productConverter) {
    }

    public CartProductDTO mapToDTO(CartProduct cartProduct){
        CartProductDTO cartProductDTO = new CartProductDTO();
        cartProductDTO.setProductDTO(productConverter.mapEntityToDTO(cartProduct.getProduct()));
        cartProductDTO.setQuantity(cartProduct.getQuantity());
        return cartProductDTO;
    }

    public List<CartProductDTO> mapToDTOs(List<CartProduct> products) {
        List<CartProductDTO> productDTOList = new ArrayList<>();
        for (CartProduct cart : products) {
            productDTOList.add(mapToDTO(cart));
        }
        return productDTOList;
    }

}
