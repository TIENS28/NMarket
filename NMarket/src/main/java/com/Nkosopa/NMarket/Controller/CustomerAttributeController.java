package com.Nkosopa.NMarket.Controller;

import com.Nkosopa.NMarket.Services.Customer.impl.CustomerAttributeServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/auth/customer/attributes")
public class CustomerAttributeController {

    @Autowired
    private CustomerAttributeServiceImpl customerAttributeService;

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
