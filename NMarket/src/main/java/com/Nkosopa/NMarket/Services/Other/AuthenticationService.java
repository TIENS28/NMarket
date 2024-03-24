package com.Nkosopa.NMarket.Services.Other;

import com.Nkosopa.NMarket.DTO.Authentication.AuthenticationRequest;
import com.Nkosopa.NMarket.DTO.Authentication.AuthenticationResponse;
import com.Nkosopa.NMarket.DTO.Authentication.RegistrationRequest;
import com.Nkosopa.NMarket.Entity.Customer.Customer;
import com.Nkosopa.NMarket.Entity.Customer.Role;
import com.Nkosopa.NMarket.Repository.Customer.JPA.CustomerJPARepository;
import com.Nkosopa.NMarket.Services.Customer.impl.RegistrationServiceImpl;
import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    @Autowired
    private CustomerJPARepository customerJPARepository;

    @Autowired
    private RegistrationServiceImpl registrationService;

    @Autowired
    private Cloudinary cloudinary;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private AuthenticationManager authenticationManager;

    public AuthenticationResponse registerWithVerification(RegistrationRequest request, MultipartFile avatar) {

        if (customerJPARepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("Email is already bind with other account!");
        }

        String avatarUrl;

        if (avatar == null || avatar.isEmpty()) {
            avatarUrl = "https://res.cloudinary.com/dh8vxjhie/image/upload/v1703677277/default_avatar_zcywzp.jpg";
        } else {
            avatarUrl = uploadImageToCloudinary(avatar);
        }
        var customer = Customer.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .email(request.getEmail())
                .DOB(request.getDOB())
                .password(request.getPassword())
                .address(request.getAddress())
                .gender(request.getGender())
                .avatarUrl(avatarUrl)
                .build();
        customer.setRole(Role.NORMAL_CUSTOMER);
        if (customer.getPassword() == null || customer.getPassword().isEmpty()) {
            throw new RuntimeException("Password is required");
        }else {
            registrationService.register(customer);
        }
        return AuthenticationResponse.builder()
                .message("Registration successful. Check your email for verification.")
                .build();
    }

    public String uploadImageToCloudinary(MultipartFile image) {
        try {
            Map uploadResult = cloudinary.uploader().upload(image.getBytes(), ObjectUtils.emptyMap());
            return (String) uploadResult.get("url");
        } catch (IOException e) {
            throw new RuntimeException("Failed to upload avatar to Cloudinary", e);
        }
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));

            // Check if the user is active before generating the token
            Customer customer = (Customer) authentication.getPrincipal();
            if (!customer.isStatus()) {
                throw new RuntimeException("User account is not active. Please verify your email.");
            }

            var jwtToken = jwtService.generateToken(customer);
            return AuthenticationResponse.builder()
                    .token(jwtToken)
                    .firstName(customer.getFirstName())
                    .lastName(customer.getLastName())
                    .DOB(customer.getDOB())
                    .avatarUrl(customer.getAvatarUrl())
                    .address(customer.getAddress())
                    .gender(customer.getGender())
                    .build();
        } catch (Exception e) {
            throw new RuntimeException("Authentication failed", e);
        }
    }
}
