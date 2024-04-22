package com.Nkosopa.NMarket.Controller.Product;

import com.Nkosopa.NMarket.DTO.Product.AttributeDTO;
import com.Nkosopa.NMarket.DTO.Product.ProductDTO;
import com.Nkosopa.NMarket.Services.Product.impl.AttributeServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/product/attribute")
public class AttributeController {

    @Autowired
    private AttributeServiceImpl attributeService;

    @PostMapping("/addAttribute")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    public ResponseEntity<ProductDTO> addAttributeToProduct(@RequestBody ProductDTO productDTO) {
        try {
            Long productId = productDTO.getId();
            List<AttributeDTO> attributeDTOList = productDTO.getAttributeDTOList();
            ProductDTO updatedProductDTO = attributeService.addAttributesToProduct(productId, attributeDTOList);
            return ResponseEntity.ok(updatedProductDTO);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping("/addToAllProduct")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    public ResponseEntity<AttributeDTO> addAttributeToAllProducts(@RequestBody AttributeDTO attributeDTO) {
        AttributeDTO attributeDTO1 = attributeService.addAttributeToAllProducts(attributeDTO);
        return ResponseEntity.ok(attributeDTO1);
    }

    @GetMapping("/all")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    public ResponseEntity<List<AttributeDTO>> getAllAttributes() {
        List<AttributeDTO> attributeDTOList = attributeService.getAllAttribute();
        return ResponseEntity.ok(attributeDTOList);
    }

}
