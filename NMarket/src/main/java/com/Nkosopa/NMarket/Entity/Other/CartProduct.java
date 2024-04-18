package com.Nkosopa.NMarket.Entity.Other;

import com.Nkosopa.NMarket.DTO.Product.ProductDTO;
import com.Nkosopa.NMarket.Entity.BaseEntity;
import com.Nkosopa.NMarket.Entity.Product.Product;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;


@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class CartProduct extends BaseEntity<CartProduct> {

    @ManyToOne
    @JoinColumn(name = "cart_id")
    private ShoppingCart cart;

    @ManyToOne
    @JoinColumn(name = "order_id")
    private OrderList order;

    @OneToOne
    @JoinColumn(name = "product_id")
    private Product product;

    private Long quantity;
}
