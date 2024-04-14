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
@Table(name = "product_entity_int")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductLongValue extends BaseEntity<ProductLongValue>{

    @ManyToOne
    @JoinColumn(name = "attribute_id")
    private AttributeEAV attribute;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;
	
	private Long value;
}
