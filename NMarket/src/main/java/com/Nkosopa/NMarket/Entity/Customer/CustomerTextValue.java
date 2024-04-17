package com.Nkosopa.NMarket.Entity.Customer;

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

@Entity
@Table(name = "customer_entity_text")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CustomerTextValue extends BaseEntity<CustomerTextValue>{

    @ManyToOne
    @JoinColumn(name = "attribute_id")
    private CustomerAttributeEAV attribute;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;
	
	private String value;
}
