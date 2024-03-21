package com.Nkosopa.NMarket.Services.Customer;

import com.Nkosopa.NMarket.Entity.Customer.Customer;
import com.Nkosopa.NMarket.Entity.Product.Product;

import java.util.List;

public interface iShoppingCartService {
    void addProductToCart(Customer customer, List<Product> products);

    Long calculateTotalPrice(List<Product> products);
}
