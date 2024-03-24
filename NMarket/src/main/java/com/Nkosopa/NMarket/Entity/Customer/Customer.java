package com.Nkosopa.NMarket.Entity.Customer;

import com.Nkosopa.NMarket.Entity.BaseEntity;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Customer extends BaseEntity<Customer> implements UserDetails {
	
	private String firstName;
	
	private String lastName;

	private String userName;

	private String password;

	private String email; //for account registration

	private String avatarUrl;

	private String verificationToken;

	private String DOB;

	private Role role;

	@OneToOne(mappedBy = "customer")
	private ShoppingCart shoppingCart;

	private String address;

	private String gender;

	@Builder.Default
	private boolean status =  false;

	@OneToMany(mappedBy = "customer")
	private List<CustomerAttributes> attributes;

	@ManyToOne
	@JoinColumn(name = "type_id")
	private CustomerType customerType;

	@OneToMany(mappedBy = "customer")
	private List<CustomerOrder> orders;

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return List.of(new SimpleGrantedAuthority(role.name()));
	}

	@Override
	public String getUsername() {
		return email;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

	@Override
	public String getPassword() {
		return password;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) return true;
		if (obj == null || getClass() != obj.getClass()) return false;
		Customer customer = (Customer) obj;
		return id != null && id.equals(customer.id);
	}

	@Override
	public int hashCode() {
		return id != null ? id.hashCode() : 0;
	}
}
