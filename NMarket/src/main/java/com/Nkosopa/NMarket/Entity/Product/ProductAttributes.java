package com.Nkosopa.NMarket.Entity.Product;

import java.util.List;

import com.Nkosopa.NMarket.Entity.BaseEntity;

import com.Nkosopa.NMarket.Entity.DataType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "product_attribute")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductAttributes extends BaseEntity<ProductAttributes> {

	private String attribute_code;

	private String attribute_name;

	@Enumerated(EnumType.STRING)
	private DataType dataType;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;
    
	@OneToMany(mappedBy = "productAttributes")
	private List<ProductTextValue> textValues;

	@OneToMany(mappedBy = "productAttributes")
	private List<ProductLongValue> intValues;

	@OneToMany(mappedBy = "productAttributes")
	private List<ProductDateValue> dateValues;
	
}