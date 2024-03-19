package com.Nkosopa.NMarket.Entity.Customer;

import com.Nkosopa.NMarket.Entity.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.*;

import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Customer extends BaseEntity<Customer>{
	
	private String firstName;
	
	private String lastName;

	private String userName;

	private String password;

	private String email; //for account registration

	private String avatarUrl;

	private String verificationToken;

	private String DOB;

	@Builder.Default
	private boolean status =  false;

	@OneToMany(mappedBy = "customer")
	private List<CustomerAttributes> attributes;

	@ManyToOne
	@JoinColumn(name = "type_id")
	private CustomerType customerType;

	@OneToMany(mappedBy = "customer")
	private List<CustomerOrder> orders;

}
