package com.Nkosopa.NMarket.Controller;

import com.Nkosopa.NMarket.DTO.Product.ProductDTO;
import com.Nkosopa.NMarket.Services.Product.impl.ProductServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth/products")
public class ProductController {

    @Autowired
    private ProductServiceImpl productService;

    @GetMapping("/findProduct/{query}")
    public ResponseEntity<Page<ProductDTO>> searchProducts
            (
                    @PathVariable String query,
                    @RequestParam(defaultValue = "0") int page,
                    @RequestParam(defaultValue = "5") int size) {
        Page<ProductDTO> productDTOPage = productService.searchProduct(query, PageRequest.of(page, size));
        return ResponseEntity.ok(productDTOPage);
    }

    @GetMapping("/allProduct")
    public ResponseEntity<Page<ProductDTO>> allProduct(@RequestParam(defaultValue = "0") int page,
                                                       @RequestParam(defaultValue = "30") int size) {
        Page<ProductDTO> productDTOsPage = productService.listProduct(PageRequest.of(page, size));
        return ResponseEntity.ok(productDTOsPage);
    }


}



