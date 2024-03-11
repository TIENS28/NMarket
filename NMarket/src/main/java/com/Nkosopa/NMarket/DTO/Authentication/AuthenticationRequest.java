package com.Nkosopa.NMarket.DTO.Authentication;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class AuthenticationRequest {

    private String email;
    private String password;

}
