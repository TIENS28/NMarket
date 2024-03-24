package com.Nkosopa.NMarket.DTO.Authentication;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AuthenticationResponse {
    private String token;
    private String firstName;
    private String lastName;
    private String DOB;
    private Long avatarFileSize;
    private String avatarUrl;
    private String message;
    private String address;
    private String gender;
}
