package com.Nkosopa.NMarket.DTO.Authentication;

import jakarta.annotation.Nullable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegistrationRequest {

    private String firstName;

    private String lastName;

    private String userName;

    private String password;

    private String email;

    private String DOB;

    @Nullable
    private MultipartFile avatar;
}
