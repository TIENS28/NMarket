package com.Nkosopa.NMarket.Controller.Product;

import com.Nkosopa.NMarket.DTO.Product.AttributeDTO;
import com.Nkosopa.NMarket.DTO.Product.ProductDTO;
import com.Nkosopa.NMarket.Services.Product.impl.AttributeServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/attribute")
public class AttributeController {

    @Autowired
    private AttributeServiceImpl attributeService;

    @PostMapping("/addAttribute")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_CUSTOMER')")
    public ResponseEntity<ProductDTO> addAttributeToProduct(@RequestBody ProductDTO productDTO) {
        try {
            Long productId = productDTO.getId();
            AttributeDTO attributeDTO = productDTO.getAttributeDTOList().get(0);
            ProductDTO updatedProductDTO = attributeService.addAttributeToProduct(productId, attributeDTO);
            return ResponseEntity.ok(updatedProductDTO);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping("/addToAllProduct")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_CUSTOMER')")
    public ResponseEntity<String> addAttributeToAllProducts(@RequestBody AttributeDTO attributeDTO) {
        try {
            attributeService.addAttributeToAllProducts(attributeDTO);
            return new ResponseEntity<>("Attribute added to all products successfully", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
