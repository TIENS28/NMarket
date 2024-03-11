package com.Nkosopa.NMarket.Controller;

import com.Nkosopa.NMarket.DTO.Authentication.AuthenticationResponse;
import com.Nkosopa.NMarket.DTO.Authentication.RegistrationRequest;
import com.Nkosopa.NMarket.Services.Customer.impl.RegistrationServiceImpl;
import com.Nkosopa.NMarket.Services.Other.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/auth")
public class AuthenticationController {

    @Autowired
    private AuthenticationService authenticationService;

    @Autowired
    private RegistrationServiceImpl registrationService;

    @PostMapping("/register")
    public ResponseEntity<?> register(@ModelAttribute RegistrationRequest request,
                                      @RequestParam(name = "avatar", required = false) MultipartFile avatar) {
        AuthenticationResponse authenticationResponse = authenticationService.registerWithVerification(request, avatar);
        if (authenticationResponse != null) {
            return ResponseEntity.ok(authenticationResponse);
        } else {
            return ResponseEntity.badRequest().body(authenticationResponse);
        }
    }

    @GetMapping("/confirm")
    public ResponseEntity<String> confirmRegistration(@RequestParam("token") String token) {
        if (registrationService.confirmRegistration(token)) {
            return ResponseEntity.ok("Registration confirmed successfully.");
        } else {
            return ResponseEntity.badRequest().body("Invalid or expired token.");
        }
    }

}
