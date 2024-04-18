package com.Nkosopa.NMarket.Entity.Other;

import com.Nkosopa.NMarket.Entity.BaseEntity;
import com.Nkosopa.NMarket.Entity.Customer.Customer;
import com.Nkosopa.NMarket.Entity.Product.Product;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderList extends BaseEntity<OrderList> {

    @ManyToMany(cascade = { CascadeType.PERSIST, CascadeType.MERGE })
    private List<Product> productList;

    private LocalDateTime oderDate;

    private Long totalPrice;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CartProduct> cartProductList;

    private boolean isShipped = false;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id")
    private Customer customer;

}
