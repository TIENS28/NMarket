package com.Nkosopa.NMarket.Services.Product;

import com.Nkosopa.NMarket.DTO.Product.ProductDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public interface iProductService{

    List<ProductDTO> addProducts(List<ProductDTO> productDTOs);

    Optional<ProductDTO> findProductById(Long productId);

    //search product
    Page<ProductDTO> searchProduct(String query, org.springframework.data.domain.Pageable pageable);


    ProductDTO updateProduct(ProductDTO productDTO);
}
