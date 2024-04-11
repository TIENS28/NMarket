package com.Nkosopa.NMarket.Controller.Product;

import com.Nkosopa.NMarket.DTO.Product.ProductAttributesDTO;
import com.Nkosopa.NMarket.Services.Product.impl.ProductAttributeServiceImpl;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/products/")
public class ProductAttributeController {

    @Autowired
    private ProductAttributeServiceImpl productAttributeService;

    @PostMapping("/addAttributes")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<List<ProductAttributesDTO>> addAttributeToAllProduct(@RequestBody List<ProductAttributesDTO> productAttributeDTOList) {
        try {
            List<ProductAttributesDTO> addedAttributes = productAttributeService.addAttributeToAllProduct(productAttributeDTOList);
            return ResponseEntity.ok(addedAttributes);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @DeleteMapping("/attribute/deleteSingle")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<String> deleteSingleProductAttribute(@RequestParam("productId") Long productId, @RequestParam("attributeCodes") List<String> attributeCodes) {
        try {
            productAttributeService.deleteSingleProductAttribute(productId, attributeCodes);
            return ResponseEntity.ok("Attributes deleted from product successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @DeleteMapping("/attribute/deleteAll")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<String> deleteAttributesOfAllProduct(@RequestBody List<String> attributeCodes) {
        try {
            productAttributeService.deleteAttributesOfAllProduct(attributeCodes);
            return ResponseEntity.ok("Attributes deleted from all products successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
