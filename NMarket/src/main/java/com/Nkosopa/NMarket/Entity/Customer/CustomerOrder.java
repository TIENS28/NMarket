package com.Nkosopa.NMarket.Entity.Customer;

import com.Nkosopa.NMarket.Entity.BaseEntity;
import com.Nkosopa.NMarket.Entity.Product.Product;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class CustomerOrder extends BaseEntity<CustomerOrder> {

    @ManyToMany
    private List<Product> productList;

    private Date oderDate;

    private Long totalPrice;

    private boolean isShipped;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;
}
