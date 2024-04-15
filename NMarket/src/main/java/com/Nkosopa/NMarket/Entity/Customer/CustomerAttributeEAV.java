package com.Nkosopa.NMarket.Entity.Customer;

import com.Nkosopa.NMarket.Entity.BaseEntity;
import com.Nkosopa.NMarket.Entity.DataType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "Customer_Attribute_EAV")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class CustomerAttributeEAV extends BaseEntity <CustomerAttributeEAV> {

    @Enumerated(EnumType.STRING)
    private DataType dataType;

    private String attributeName;

    @ManyToMany(cascade = CascadeType.ALL)
    private List<Customer> customer;

    @OneToMany(mappedBy = "attribute")
    private List<CustomerTextValue> textValues;

    @OneToMany(mappedBy = "attribute")
    private List<CustomerLongValue> intValues;

    @OneToMany(mappedBy = "attribute")
    private List<CustomerDateValue> dateValues;

    private boolean isSearchable = true;
}
