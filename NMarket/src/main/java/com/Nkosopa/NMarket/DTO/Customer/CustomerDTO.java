package com.Nkosopa.NMarket.DTO.Customer;

import com.Nkosopa.NMarket.DTO.BaseDTO;
import com.Nkosopa.NMarket.DTO.Other.ShoppingCartDTO;
import jakarta.annotation.Nullable;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class CustomerDTO extends BaseDTO<CustomerDTO> {

    private String firstName;

    private String lastName;

    private String userName;

    private String password;

    private String gender;

    private String role;

    private String address;

    @Nullable
    private MultipartFile avatar;

    private String email; //for account registration

    private List<CustomerAttributeDTO> attributesDTO;

    private ShoppingCartDTO shopingCartDTO;
}
