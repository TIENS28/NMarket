package com.Nkosopa.NMarket.Services.Other;

import com.Nkosopa.NMarket.DTO.Other.CartProductDTO;
import com.Nkosopa.NMarket.DTO.Other.ShoppingCartDTO;
import com.Nkosopa.NMarket.Entity.Customer.Customer;
import com.Nkosopa.NMarket.Entity.Product.Product;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public interface iShoppingCartService {


    ShoppingCartDTO addProductsToCart(List<CartProductDTO> cartProductDTOList);

    ShoppingCartDTO removeProductFromShoppingCart(List<Long> productId, Long cartId);

    ShoppingCartDTO cancelShoppingCart(Long cartId);
}
