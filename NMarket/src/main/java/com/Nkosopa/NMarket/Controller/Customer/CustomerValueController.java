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

    @DeleteMapping("/delete")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_CUSTOMER')")
    public ResponseEntity<String> delete(@RequestBody CustomerDTO customerDTO) {
        Long customerId = customerDTO.getId();
        customerValueService.deleteCustomerValue(customerId);
        return ResponseEntity.ok("Delete successful");
    }
}
