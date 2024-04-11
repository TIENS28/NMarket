package com.Nkosopa.NMarket.Controller.Customer;

import com.Nkosopa.NMarket.DTO.Customer.CustomerDTO;
import com.Nkosopa.NMarket.DTO.Customer.CustomerValueDTO;
import com.Nkosopa.NMarket.Services.Customer.impl.CustomerValueServiceImpl;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/value")
public class CustomerValueController {

    @Autowired
    private CustomerValueServiceImpl customerValueService;

    @PostMapping("/addValues")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_CUSTOMER')")
    public ResponseEntity<CustomerValueDTO> addValueToCustomerAttribute(@RequestParam("customerAttributeId") Long customerAttributeId, @RequestBody CustomerValueDTO valueDTO) {
        try {
            CustomerValueDTO newValuesDTO = customerValueService.addValueToCustomerAttribute(customerAttributeId, valueDTO);
            return ResponseEntity.ok(newValuesDTO);
        } catch (IllegalArgumentException | EntityNotFoundException e) {
            return ResponseEntity.badRequest().body(null);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @PutMapping("/updateAttributeValue/{attributeId}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_CUSTOMER')")
    public ResponseEntity<CustomerValueDTO> updateCustomerAttributeValue(@PathVariable("attributeId") Long attributeId, @RequestBody CustomerValueDTO customerValueDTO) {
        CustomerValueDTO updatedCustomerValueDTO = customerValueService.updateCustomerAttributeValue(attributeId, customerValueDTO);
        return ResponseEntity.ok(updatedCustomerValueDTO);
    }
}
