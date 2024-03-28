package com.Nkosopa.NMarket.Controller.Product;

import com.Nkosopa.NMarket.DTO.Product.ProductDTO;
import com.Nkosopa.NMarket.Services.Product.impl.ProductServiceImpl;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/product")
public class ProductController {

    @Autowired
    private ProductServiceImpl productServiceImple;

    @GetMapping("/findProduct/{query}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_CUSTOMER')")
    public ResponseEntity<Page<ProductDTO>> searchProducts
            (
                    @PathVariable String query,
                    @RequestParam(defaultValue = "0") int page,
                    @RequestParam(defaultValue = "5") int size) {
        Page<ProductDTO> productDTOPage = productServiceImple.searchProduct(query, PageRequest.of(page, size));
        return ResponseEntity.ok(productDTOPage);
    }

    @GetMapping("/allProduct")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_CUSTOMER')")
    public ResponseEntity<Page<ProductDTO>> allProduct(@RequestParam(defaultValue = "0") int page,
                                                       @RequestParam(defaultValue = "30") int size) {
        Page<ProductDTO> productDTOsPage = productServiceImple.listProduct(PageRequest.of(page, size));
        return ResponseEntity.ok(productDTOsPage);
    }

    @PostMapping("/addProducts")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_CUSTOMER')")
    public ResponseEntity<List<ProductDTO>> addMultipleProducts(@RequestBody List<ProductDTO> productDTOs) {
        List<ProductDTO> createdProducts = productServiceImple.addProducts(productDTOs);
        return new ResponseEntity<>(createdProducts, HttpStatus.CREATED);
    }

    @PutMapping("/updateProduct")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_CUSTOMER')")
    public ResponseEntity<?> updateProduct(@RequestParam("productId") Long productId, @RequestBody ProductDTO productDTO) {
        try {
            ProductDTO updatedProductDTO = productServiceImple.updateProduct(productId, productDTO);
            return ResponseEntity.ok(updatedProductDTO);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error updating product: " + e.getMessage());
        }
    }


}



