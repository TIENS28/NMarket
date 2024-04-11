package com.Nkosopa.NMarket.Entity.Product;

import com.Nkosopa.NMarket.Entity.BaseEntity;
import com.Nkosopa.NMarket.Entity.DataType;
import jakarta.persistence.*;
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
public class AttributeEAV extends BaseEntity {

    private String name;

    @ManyToMany(cascade = CascadeType.ALL)
    private List<Product> product;

    @OneToMany(mappedBy = "attribute")
    private List<ProductTextValue> textValues;

    @OneToMany(mappedBy = "attribute")
    private List<ProductLongValue> intValues;

    @OneToMany(mappedBy = "attribute")
    private List<ProductDateValue> dateValues;

    private boolean isSearchable = true;

    @Enumerated(EnumType.STRING)
    private DataType dataType;

}
