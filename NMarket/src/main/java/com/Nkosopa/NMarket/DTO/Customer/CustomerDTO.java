package com.Nkosopa.NMarket.DTO.Customer;

import com.Nkosopa.NMarket.Entity.Customer.CustomerAttributes;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class CustomerDTO {

    private String firstName;

    private String lastName;

    private String userName;

    private String password;

    private String email; //for account registration

    private List<CustomerAttributeDTO> attributesDTO;
}
