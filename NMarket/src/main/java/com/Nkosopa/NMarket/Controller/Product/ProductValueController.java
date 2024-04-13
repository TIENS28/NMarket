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

}
