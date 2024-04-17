package com.Nkosopa.NMarket.Controller.Customer;

import com.Nkosopa.NMarket.DTO.Customer.CustomerAttributeDTO;
import com.Nkosopa.NMarket.DTO.Customer.CustomerAttributeEAVDTO;
import com.Nkosopa.NMarket.DTO.Customer.CustomerDTO;
import com.Nkosopa.NMarket.DTO.Product.AttributeDTO;
import com.Nkosopa.NMarket.DTO.Product.ProductDTO;
import com.Nkosopa.NMarket.Services.Customer.impl.CustomerAttributeServiceImpl;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/customer")
public class CustomerAttributeController {

    @Autowired
    private CustomerAttributeServiceImpl customerAttributeService;

//    @PostMapping("/{customerId}/addAttributes")
//    @PreAuthorize("hasRole('ROLE_ADMIN')")
//    public ResponseEntity<String> addAttributeToOneCustomer(@PathVariable Long customerId, @RequestBody List<CustomerAttributeEAVDTO> attributeDTOs) {
//        try {
//            customerAttributeService.addAttributeToOneCustomer(customerId, attributeDTOs);
//            return ResponseEntity.ok("Attributes added to customer successfully");
//        } catch (EntityNotFoundException e) {
//            return ResponseEntity.notFound().build();
//        }
//    }

    @PostMapping("/addAttribute")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_CUSTOMER')")
    public ResponseEntity<CustomerDTO> addAttributeToCustomer(@RequestBody CustomerDTO customerDTO) {
        try {
            Long customerId = customerDTO.getId();
            List<CustomerAttributeEAVDTO> attributeDTOList = customerDTO.getAttributeEAVDTOList();
            CustomerDTO updatedCustomerDTO = customerAttributeService.addAttributesToCustomer(customerId, attributeDTOList);
            return ResponseEntity.ok(updatedCustomerDTO);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

//    @DeleteMapping("/{customerId}/deleteAttribute")
//    @PreAuthorize("hasRole('ROLE_ADMIN')")
//    public ResponseEntity<String> deleteCustomerAttributes(
//            @PathVariable Long customerId,
//            @RequestBody List<String> attributeCodes
//    ) {
//        customerAttributeService.deleteSingleCustomerAttribute(customerId, attributeCodes);
//        return ResponseEntity.ok("Customer attributes deleted successfully");
//    }


}
