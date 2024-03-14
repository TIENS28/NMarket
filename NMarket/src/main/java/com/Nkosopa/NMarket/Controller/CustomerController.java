package com.Nkosopa.NMarket.Controller;

import com.Nkosopa.NMarket.DTO.Customer.CustomerDTO;
import com.Nkosopa.NMarket.DTO.Customer.CustomerValueDTO;
import com.Nkosopa.NMarket.Services.Customer.impl.CustomerServiceImpl;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/auth/customer")
public class CustomerController {

    @Autowired
    private CustomerServiceImpl customerService;

    @PostMapping("/newCustomer")
    public ResponseEntity<String> addCustomer(@RequestBody CustomerDTO customerDTO) {
        try {
            customerService.newCustomer(customerDTO);
            return new ResponseEntity<>("Customer added successfully", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Error adding customer", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/{customerAttributeId}/addValue")
    public ResponseEntity<String> addValueToCustomerAttribute(
            @PathVariable Long customerAttributeId,
            @RequestBody CustomerValueDTO valueDTO
    ) {
        try {
            customerService.addValueToCustomerAttribute(customerAttributeId, valueDTO);
            return new ResponseEntity<>("Value added successfully", HttpStatus.OK);
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>("CustomerAttribute not found", HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/addValues")
    public ResponseEntity<String> addValuesToCustomerAttributes(@RequestBody List<CustomerValueDTO> valueDTOs) {
        try {
            customerService.addValuesToCustomerAttributes(valueDTOs);
            return ResponseEntity.ok("Values added to customer attributes successfully");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error adding values to customer attributes");
        }
    }

    @GetMapping("/{customerId}")
    public ResponseEntity<CustomerDTO> getCustomerById(@PathVariable Long customerId) {
        Optional<CustomerDTO> customerDTOOptional = customerService.findCustomerById(customerId);

        return customerDTOOptional
                .map(customerDTO -> new ResponseEntity<>(customerDTO, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @DeleteMapping("/delete/{customerId}")
    public ResponseEntity<String> deleteCustomer(@PathVariable long customerId) {
        customerService.deleteUser(customerId);
        return ResponseEntity.ok("Delete customer successfully");
    }

    @DeleteMapping("/attributes/delete/{customerId}")
    public ResponseEntity<String> deleteAttributes(@PathVariable long customerId) {
        customerService.deleteUser(customerId);
        return ResponseEntity.ok("Delete customer successfully");
    }

    @GetMapping("/allCustomer")
    public ResponseEntity<List<CustomerDTO>> getAllCustomer() {
        List<CustomerDTO> customerDTOs = customerService.getAllCustomer();
        return ResponseEntity.ok(customerDTOs);
    }

    @GetMapping("/{customerId}/information")
    public ResponseEntity<CustomerDTO> getCustomerInformation(@PathVariable Long customerId){
        CustomerDTO customerDTO = customerService.getOneCustomer(customerId);
        return ResponseEntity.ok(customerDTO);
    }
}