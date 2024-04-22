package com.Nkosopa.NMarket.Entity.Product;

import com.Nkosopa.NMarket.Entity.BaseEntity;
import com.Nkosopa.NMarket.Entity.Other.OrderList;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Product extends BaseEntity<Product>{
	
	private String sku;
	
	private String name;

	private String currency;

	private Long stock;

	private Double price;

	@ManyToOne
	@JoinColumn(name = "type_id")
	private ProductType productType;

	@ManyToMany(cascade = CascadeType.ALL)
	private List<AttributeEAV> attributeEAVS;

	@ManyToMany(cascade = CascadeType.ALL)
	private List<OrderList> orderLists;

	@OneToMany(mappedBy = "product")
	private List<ProductTextValue> textValues;

	@OneToMany(mappedBy = "product")
	private List<ProductLongValue> intValues;

	@OneToMany(mappedBy = "product")
	private List<ProductDateValue> dateValues;
}
