package com.Nkosopa.NMarket.Entity.Product;

import com.Nkosopa.NMarket.Entity.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
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
public class Product extends BaseEntity<Product>{
	
	private String sku;
	
	private String name;
	
	private int stock;
	
	@OneToMany(mappedBy = "product")
	private List<ProductAttributes> attributes;

	@ManyToOne
	@JoinColumn(name = "type_id")
	private ProductType productType;
}
