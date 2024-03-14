package com.Nkosopa.NMarket.Controller;

import com.Nkosopa.NMarket.DTO.Customer.CustomerAttributeDTO;
import com.Nkosopa.NMarket.Services.Customer.impl.CustomerAttributeServiceImpl;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/auth/customer/attributes")
public class CustomerAttributeController {

    @Autowired
    private CustomerAttributeServiceImpl customerAttributeService;

    @PostMapping("/{customerId}/addAttributes")
    public ResponseEntity<String> addAttributeToOneCustomer(@PathVariable Long customerId, @RequestBody List<CustomerAttributeDTO> attributeDTOs) {
        try {
            customerAttributeService.addAttributeToOneCustomer(customerId, attributeDTOs);
            return ResponseEntity.ok("Attributes added to customer successfully");
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/addAttributes")
    public ResponseEntity<String> addAttributesToAllCustomers(@RequestBody List<CustomerAttributeDTO> attributeDTOs) {
        try {
            customerAttributeService.addAttributesToAllCustomers(attributeDTOs);
            return ResponseEntity.ok("Attributes added to all customers successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error adding attributes to all customers");
        }
    }

    @DeleteMapping("/{customerId}/deleteAttribute")
    public ResponseEntity<String> deleteCustomerAttributes(
            @PathVariable Long customerId,
            @RequestBody List<String> attributeCodes
    ) {
        customerAttributeService.deleteSingleCustomerAttribute(customerId, attributeCodes);
        return ResponseEntity.ok("Customer attributes deleted successfully");
    }

    @DeleteMapping("/deleteAttribute")
    public ResponseEntity<String> deleteCustomerAttributes(
            @RequestBody List<String> attributeCodes
    ) {
        customerAttributeService.deleteAttributesOfAllCustomer(attributeCodes);
        return ResponseEntity.ok("Customer attributes deleted successfully");
    }


}
