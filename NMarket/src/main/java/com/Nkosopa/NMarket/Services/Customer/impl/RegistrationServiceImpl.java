package com.Nkosopa.NMarket.Services.Customer.impl;

import com.Nkosopa.NMarket.Entity.Customer.Customer;
import com.Nkosopa.NMarket.Repository.Customer.JPA.CustomerJPARepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class RegistrationServiceImpl {

    @Autowired
    private CustomerJPARepository customerJPARepository;

    @Autowired
    private JavaMailSender javaMailSender;

    @Autowired
    private PasswordEncoder passwordEncoder;
    public void register(Customer customer) {

        customer.setPassword(passwordEncoder.encode(customer.getPassword()));
        customer.setStatus(false);
        customer.setVerificationToken(UUID.randomUUID().toString());
        customerJPARepository.save(customer);

        // Send the verification email
        sendVerificationEmail(customer.getEmail(), customer.getVerificationToken());
    }

    private void sendVerificationEmail(String email, String token) {
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(email);
        mailMessage.setSubject("HUTECH Forum - Email Verification");
        mailMessage.setFrom("hutechforum@gmail.com");

        String verificationLink = "http://localhost:5005/api/v1/auth/confirm?token=" + token;
        String emailBody = "Please click the following link to verify your email:\n" + verificationLink;

        mailMessage.setText(emailBody);

        javaMailSender.send(mailMessage);
    }

    public boolean confirmRegistration(String token) {
        // Find the customer by the verification token
        Customer customer = customerJPARepository.findByVerificationToken(token);

        if (customer != null) {
            // Set the customer's status to true and remove the verification token
            customer.setStatus(true);
            customer.setVerificationToken(null);
            customerJPARepository.save(customer);
            return true;
        }

        return false;
    }
}
