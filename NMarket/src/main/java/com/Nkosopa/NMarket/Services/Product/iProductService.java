package com.Nkosopa.NMarket.Services.Product;

import com.Nkosopa.NMarket.DTO.Product.ProductDTO;
import org.springframework.stereotype.Service;

@Service
public interface iProductService{


    void addProduct(ProductDTO productDTO)//add new customer manually
    ;
}
