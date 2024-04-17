package com.Nkosopa.NMarket.Entity.Customer;

import java.util.Date;

import com.Nkosopa.NMarket.Entity.BaseEntity;

import com.Nkosopa.NMarket.Entity.Product.AttributeEAV;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "Customer_Date_value")
public class CustomerDateValue extends BaseEntity<CustomerDateValue>{


    @ManyToOne
    @JoinColumn(name = "attribute_id")
    private CustomerAttributeEAV attribute;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;
    
    private Date value;
   
}
