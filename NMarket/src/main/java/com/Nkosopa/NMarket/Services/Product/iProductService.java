package com.Nkosopa.NMarket.Services.Product;

import com.Nkosopa.NMarket.DTO.Product.ProductDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface iProductService{


    void addProduct(ProductDTO productDTO);//add new customer manually

    Optional<ProductDTO> findProductById(Long productId);

    List<ProductDTO> getAllProduct();

    //search product
    Page<ProductDTO> searchProduct(String query, org.springframework.data.domain.Pageable pageable);

    Page<ProductDTO> listProduct(Pageable pageable);

    void updateProduct(Long productId, ProductDTO updatedProductDTO);
}
