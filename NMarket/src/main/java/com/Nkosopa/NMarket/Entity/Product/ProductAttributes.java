package com.Nkosopa.NMarket.Entity.Product;

import com.Nkosopa.NMarket.Entity.BaseEntity;
import com.Nkosopa.NMarket.Entity.DataType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

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

    @ManyToOne(fetch = FetchType.EAGER )
    @JoinColumn(name = "product_id")
    private Product product;
    
	@OneToMany(mappedBy = "productAttributes")
	private List<ProductTextValue> textValues;

	@OneToMany(mappedBy = "productAttributes")
	private List<ProductLongValue> intValues;

	@OneToMany(mappedBy = "productAttributes")
	private List<ProductDateValue> dateValues;

	private boolean isSearchable;
	
}