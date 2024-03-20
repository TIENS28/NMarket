package com.Nkosopa.NMarket.Controller;

import com.Nkosopa.NMarket.DTO.Product.ProductAttributesDTO;
import com.Nkosopa.NMarket.Services.Product.impl.ProductAttributeServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/auth/products/attribute")
public class ProductAttributeController {

    @Autowired
    private ProductAttributeServiceImpl productAttributeService;

    @PostMapping("/addAttributes")
    public ResponseEntity<String> addAttributesToAllCustomers(@RequestBody List<ProductAttributesDTO> attributeDTOs) {
        try {
            productAttributeService.addAttributeToAllProduct(attributeDTOs);
            return ResponseEntity.ok("Attributes added to all product successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error adding attributes to all customers");
        }
    }

    @DeleteMapping("/{productId}/deleteAttribute")
    public ResponseEntity<String> deletepProductAttributes(
            @PathVariable Long productId,
            @RequestBody List<String> attributeCodes
    ) {
        productAttributeService.deleteSingleProductAttribute(productId, attributeCodes);
        return ResponseEntity.ok("Product attributes deleted successfully");
    }

    @DeleteMapping("/deleteAttribute")
    public ResponseEntity<String> deleteCustomerAttributes(
            @RequestBody List<String> attributeCodes
    ) {
        productAttributeService.deleteAttributesOfAllProduct(attributeCodes);
        return ResponseEntity.ok("Products attributes deleted successfully");
    }
}
