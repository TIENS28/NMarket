package com.Nkosopa.NMarket.Services.Product;

import com.Nkosopa.NMarket.DTO.Product.ProductDTO;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface iProductService{


    void addProduct(ProductDTO productDTO);//add new customer manually

    Optional<ProductDTO> findProductById(Long productId);

    List<ProductDTO> getAllProduct();
}
