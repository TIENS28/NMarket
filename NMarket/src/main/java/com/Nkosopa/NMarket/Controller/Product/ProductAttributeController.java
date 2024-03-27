package com.Nkosopa.NMarket.Controller.Product;

import com.Nkosopa.NMarket.DTO.Product.ProductAttributesDTO;
import com.Nkosopa.NMarket.Services.Product.impl.ProductAttributeServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/products/attribute")
public class ProductAttributeController {

    @Autowired
    private ProductAttributeServiceImpl productAttributeService;

    @PostMapping("/addAttributes")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> addAttributesToAllCustomers(@RequestBody List<ProductAttributesDTO> attributeDTOs) {
        try {
            productAttributeService.addAttributeToAllProduct(attributeDTOs);
            return ResponseEntity.ok("Attributes added to all product successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error adding attributes to all customers");
        }
    }

    @DeleteMapping("/deleteAttribute")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> deleteCustomerAttributes(
            @RequestBody List<String> attributeCodes
    ) {
        productAttributeService.deleteAttributesOfAllProduct(attributeCodes);
        return ResponseEntity.ok("Products attributes deleted successfully");
    }
}
