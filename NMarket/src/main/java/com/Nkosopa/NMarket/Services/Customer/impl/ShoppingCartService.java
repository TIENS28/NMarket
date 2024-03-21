package com.Nkosopa.NMarket.Services.Customer.impl;

import com.Nkosopa.NMarket.Entity.Customer.Customer;
import com.Nkosopa.NMarket.Entity.Customer.ShoppingCart;
import com.Nkosopa.NMarket.Entity.Product.Product;
import com.Nkosopa.NMarket.Entity.Product.ProductAttributes;
import com.Nkosopa.NMarket.Entity.Product.ProductLongValue;
import com.Nkosopa.NMarket.Services.Customer.iShoppingCartService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ShoppingCartService implements iShoppingCartService {

    @Override
    public void addProductToCart(Customer customer, List<Product> products) {

        ShoppingCart shoppingCart =  customer.getShoppingCart();

        if(shoppingCart == null){
            shoppingCart =  new ShoppingCart();
            shoppingCart.setCustomer(customer);
            shoppingCart.setProductList(products);
        }

        shoppingCart.getProductList().addAll(products);

        Long totalPrice = calculateTotalPrice(shoppingCart.getProductList());

        shoppingCart.setTotalPrice(totalPrice);
    }

    @Override
    public Long calculateTotalPrice(List<Product> products) {
        Long totalPrice = 0L;
        for (Product product : products) {
            // Retrieve the price attribute value for each product
            Long priceAttributeValue = getPriceAttributeValue(product);
            if (priceAttributeValue != null) {
                totalPrice += priceAttributeValue;
            }
        }
        return totalPrice;
    }

    private Long getPriceAttributeValue(Product product) {
        List<ProductAttributes> attributes = product.getAttributes();
        if (attributes != null) {
            for (ProductAttributes attribute : attributes) {
                if ("price".equals(attribute.getAttribute_code())) {
                    List<ProductLongValue> intValues = attribute.getIntValues();
                    if (intValues != null && !intValues.isEmpty()) {
                        return intValues.get(0).getValue();
                    }
                }
            }
        }
        return null;
    }
}