package com.Nkosopa.NMarket.Entity.Other;

import com.Nkosopa.NMarket.Entity.BaseEntity;
import com.Nkosopa.NMarket.Entity.Customer.Customer;
import com.Nkosopa.NMarket.Entity.Product.Product;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ShoppingCart extends BaseEntity<ShoppingCart> {

    @OneToMany
    private List<Product> productList;

    @OneToMany(mappedBy = "cart", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CartProduct> cartProductsList;

    @OneToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;

    private Double totalPrice;

}
