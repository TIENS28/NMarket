package com.Nkosopa.NMarket.Controller.Product;

import com.Nkosopa.NMarket.DTO.Product.ProductDTO;
import com.Nkosopa.NMarket.DTO.Product.ProductValueDTO;
import com.Nkosopa.NMarket.Services.Product.impl.ProductServiceImpl;
import com.Nkosopa.NMarket.Services.Product.impl.ProductValueServiceImpl;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/products/attribute/value")
public class ProductValueController {

    @Autowired
    private ProductValueServiceImpl productValueService;

    @Autowired
    private ProductServiceImpl productService;

//    @PostMapping("/{productAttributeId}/addValue")
//    public ResponseEntity<String> addValueToProductAttribute(
//            @PathVariable Long productAttributeId,
//            @RequestBody ProductValueDTO valueDTO
//    ) {
//        try {
//            productValueService.addValueToProductAttribute(productAttributeId, valueDTO);
//            return new ResponseEntity<>("Value added successfully", HttpStatus.OK);
//        } catch (EntityNotFoundException e) {
//            return new ResponseEntity<>("Product attribute not found", HttpStatus.NOT_FOUND);
//        }
//    }

    @PostMapping("/addValues")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_CUSTOMER')")
    public ResponseEntity<List<ProductDTO>> addValuesToProductAttributes(@RequestBody List<ProductValueDTO> valueDTOs) {
        try {
            List<ProductDTO> productDTO = productValueService.addValuesToProductAttributes(valueDTOs);
            return ResponseEntity.ok(productDTO);
        } catch(EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/updateValue")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_CUSTOMER')")
    public ResponseEntity<ProductDTO> updateProductAttributeValue(@RequestBody ProductValueDTO valueDTO) {
        ProductDTO updatedProductDTO = productValueService.updateValueOfProductAttribute(valueDTO);
        if (updatedProductDTO != null) {
            return ResponseEntity.ok(updatedProductDTO);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/updateValues")
    public ResponseEntity<ProductDTO> updateProductAttributesValues(@RequestBody List<ProductValueDTO> valueDTOs) {
        ProductDTO updatedProductDTO = productValueService.updateProductAttributeValues(valueDTOs);
        if (updatedProductDTO != null) {
            return ResponseEntity.ok(updatedProductDTO);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
