package com.Nkosopa.NMarket.Controller.Product;

import com.Nkosopa.NMarket.DTO.Product.ProductDTO;
import com.Nkosopa.NMarket.Services.Product.impl.ProductServiceImpl;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

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

//    @GetMapping("/findProductWithFilter")
//    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_CUSTOMER')")
//    public ResponseEntity<Page<ProductDTO>> searchProductsWithFilter(
//            @RequestParam(required = false) String name,
//            @RequestParam(required = false) Map<String, String> filters,
//            @RequestParam(defaultValue = "0") int page,
//            @RequestParam(defaultValue = "5") int size
//    ) {
//        Page<ProductDTO> productDTOPage = productServiceImple.searchProductWithFilter(name, filters, PageRequest.of(page, size));
//        return ResponseEntity.ok(productDTOPage);
//    }

    @GetMapping("/information")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_CUSTOMER')")
    public ResponseEntity<ProductDTO> getProductById(@RequestBody ProductDTO productDTO) {
        Long productId = productDTO.getId();
        Optional<ProductDTO> productDTOOptional = productServiceImple.findProductById(productId);
        return productDTOOptional.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("/addProducts")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_CUSTOMER')")
    public ResponseEntity<List<ProductDTO>> addProducts(@RequestBody List<ProductDTO> productDTOs) {
        if (productDTOs == null || productDTOs.isEmpty()) {
            return ResponseEntity.badRequest().body(null);
        }
        List<ProductDTO> createdProducts;
        createdProducts = productServiceImple.addProducts(productDTOs);
        return ResponseEntity.ok(createdProducts);
    }

    @DeleteMapping("/deleteProduct")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    public ResponseEntity<String> deleteProductById(@RequestBody ProductDTO productDTO) {
        Long productId = productDTO.getId();
        if (productId == null) {
            return ResponseEntity.badRequest().body(null);
        }else{
            productServiceImple.deleteProduct(productId);
        }
        return ResponseEntity.ok().build();
    }

    @PutMapping("/updateProduct")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_CUSTOMER')")
    public ResponseEntity<?> updateProduct(@RequestBody ProductDTO productDTO) {
        try {
            ProductDTO updatedProductDTO = productServiceImple.updateProduct(productDTO);
            return ResponseEntity.ok(updatedProductDTO);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error updating product: " + e.getMessage());
        }
    }

    @GetMapping("/search")
    public ResponseEntity<Page<ProductDTO>> searchProducts(
            @RequestParam String searchTerm,
            @RequestParam Map<String, String> allRequestParams,
            Pageable pageable) {
        Map<String, String> attributeFilters = new HashMap<>(allRequestParams);
        attributeFilters.remove("searchTerm");
        try {
            Page<ProductDTO> productPage = productServiceImple.searchWithFilter(searchTerm, attributeFilters, pageable);
            return ResponseEntity.ok(productPage);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

}



