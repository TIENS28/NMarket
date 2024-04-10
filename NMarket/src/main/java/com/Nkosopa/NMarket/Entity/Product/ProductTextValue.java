package com.Nkosopa.NMarket.Entity.Product;

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
@Table(name = "product_entity_text")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductTextValue extends BaseEntity<ProductTextValue>{
	
    @ManyToOne
    @JoinColumn(name = "product_attribute_id")
    private ProductAttributes productAttributes;

    @ManyToOne
    @JoinColumn(name = "attribute_id")
    private AttributeEAV attribute;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;
	
	private String value;
}
