package com.Nkosopa.NMarket.Entity.Customer;

import com.Nkosopa.NMarket.Entity.BaseEntity;
import com.Nkosopa.NMarket.Entity.Product.Product;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToOne;
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

    @ManyToMany
    private List<Product> productList;

    @OneToOne
    private Customer customer;

    private Long totalPrice;

}