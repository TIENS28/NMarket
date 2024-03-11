package com.Nkosopa.NMarket.Entity.Product;

import java.util.Date;

import com.Nkosopa.NMarket.Entity.BaseEntity;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "product_entity_date")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductDateValue extends BaseEntity<ProductDateValue>{
	
	@ManyToOne
    @JoinColumn(name = "product_attribute_id")
    private ProductAttributes productAttributes;
	
    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;
	
	private Date value;
}
