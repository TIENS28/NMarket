package com.Nkosopa.NMarket.Entity.Customer;

import com.Nkosopa.NMarket.Entity.BaseEntity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "customer_type")
@Entity
public class CustomerType extends BaseEntity<CustomerType>{
	
	@Column(name = "type")
	private String cType;

	@OneToMany(mappedBy = "customerType")
	private List<Customer> customers;

}
