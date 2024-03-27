package com.Nkosopa.NMarket.Controller.Customer;

import com.Nkosopa.NMarket.DTO.Customer.CustomerValueDTO;
import com.Nkosopa.NMarket.Services.Customer.impl.CustomerValueServiceImpl;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/value")
public class CustomerValueController {

    @Autowired
    private CustomerValueServiceImpl customerValueService;

    @PostMapping("/{customerAttributeId}/addValue")
    public ResponseEntity<String> addValueToCustomerAttribute(
            @PathVariable Long customerAttributeId,
            @RequestBody CustomerValueDTO valueDTO
    ) {
        try {
            customerValueService.addValueToCustomerAttribute(customerAttributeId, valueDTO);
            return new ResponseEntity<>("Value added successfully", HttpStatus.OK);
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>("CustomerAttribute not found", HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/addValues")
    public ResponseEntity<String> addValuesToCustomerAttributes(@RequestBody List<CustomerValueDTO> valueDTOs) {
        try {
            customerValueService.addValuesToCustomerAttributes(valueDTOs);
            return ResponseEntity.ok("Values added to customer attributes successfully");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error adding values to customer attributes");
        }
    }
}
