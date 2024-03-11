package com.Nkosopa.NMarket.Entity.Customer;

import java.util.List;

import com.Nkosopa.NMarket.Entity.BaseEntity;

import com.Nkosopa.NMarket.Entity.DataType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "customer_attribute")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CustomerAttributes extends BaseEntity<CustomerAttributes>{

	@Enumerated(EnumType.STRING)
	private DataType dataType;

	private String attribute_code;

	private String attribute_name;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;

	@OneToMany(mappedBy = "customerAttributes")
	private List<CustomerTextValue> textValues;

	@OneToMany(mappedBy = "customerAttributes")
	private List<CustomerLongValue> intValues;

	@OneToMany(mappedBy = "customerAttributes")
	private List<CustomerDateValue> dateValues;

}
