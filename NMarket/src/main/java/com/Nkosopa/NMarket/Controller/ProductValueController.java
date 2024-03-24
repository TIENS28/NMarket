package com.Nkosopa.NMarket.Controller;

import com.Nkosopa.NMarket.DTO.Product.ProductValueDTO;
import com.Nkosopa.NMarket.Services.Product.impl.ProductValueServiceImpl;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products/attribute")
public class ProductValueController {

    @Autowired
    private ProductValueServiceImpl productValueService;

    @PostMapping("/{productAttributeId}/addValue")
    public ResponseEntity<String> addValueToProductAttribute(
            @PathVariable Long productAttributeId,
            @RequestBody ProductValueDTO valueDTO
    ) {
        try {
            productValueService.addValueToProductAttribute(productAttributeId, valueDTO);
            return new ResponseEntity<>("Value added successfully", HttpStatus.OK);
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>("Product attribute not found", HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/addValues")
    public ResponseEntity<String> addValuesToCustomerAttributes(@RequestBody List<ProductValueDTO> valueDTOs) {
        try {
            productValueService.addValuesToProductAttributes(valueDTOs);
            return ResponseEntity.ok("Values added to customer attributes successfully");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error adding values to customer attributes");
        }
    }

    @PutMapping("/{productAttributeId}/value")
    public ResponseEntity<String> updateProductAttributeValue(@PathVariable Long productAttributeId,
                                                              @RequestBody ProductValueDTO valueDTO) {
        try {
            productValueService.updateValueOfProductAttribute(productAttributeId, valueDTO);
            return new ResponseEntity<>("Product attribute value updated successfully", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Failed to update product attribute value: " + e.getMessage(),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
