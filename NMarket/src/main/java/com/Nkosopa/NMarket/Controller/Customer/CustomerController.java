package com.Nkosopa.NMarket.Controller.Customer;

import com.Nkosopa.NMarket.DTO.Customer.CustomerDTO;
import com.Nkosopa.NMarket.DTO.Customer.CustomerValueDTO;
import com.Nkosopa.NMarket.Services.Customer.impl.CustomerServiceImpl;
import com.Nkosopa.NMarket.Services.Customer.impl.CustomerValueServiceImpl;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/customer")
public class CustomerController {

    @Autowired
    private CustomerServiceImpl customerService;

//    @PostMapping("/admin/newCustomer")
//    public ResponseEntity<String> addCustomer(@RequestBody CustomerDTO customerDTO) {
//        try {
//            customerService.newCustomer(customerDTO);
//            return new ResponseEntity<>("Customer added successfully", HttpStatus.OK);
//        } catch (Exception e) {
//            return new ResponseEntity<>("Error adding customer", HttpStatus.INTERNAL_SERVER_ERROR);
//        }
//    }

    @GetMapping("/{customerId}")
    public ResponseEntity<CustomerDTO> getCustomerById(@PathVariable Long customerId) {
        Optional<CustomerDTO> customerDTOOptional = customerService.findCustomerById(customerId);

        return customerDTOOptional
                .map(customerDTO -> new ResponseEntity<>(customerDTO, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @DeleteMapping("/delete/{customerId}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<String> deleteCustomer(@PathVariable long customerId) {
        customerService.deleteUser(customerId);
        return ResponseEntity.ok("Delete customer successfully");
    }

    @DeleteMapping("/attributes/delete/{customerId}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<String> deleteAttributes(@PathVariable long customerId) {
        customerService.deleteUser(customerId);
        return ResponseEntity.ok("Delete customer successfully");
    }

    @GetMapping("/allCustomer")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_CUSTOMER')")
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