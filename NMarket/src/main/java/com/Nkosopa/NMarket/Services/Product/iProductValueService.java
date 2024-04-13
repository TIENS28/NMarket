package com.Nkosopa.NMarket.Services.Product;

import com.Nkosopa.NMarket.DTO.Product.ProductDTO;
import com.Nkosopa.NMarket.DTO.Product.ProductValueDTO;

import java.util.List;

public interface iProductValueService {

    ProductDTO addValueToProductAttribute(ProductValueDTO valueDTO)//add value to one attribute
    ;

    List<ProductDTO> addValuesToProductAttributes(List<ProductValueDTO> valueDTOs)//add multiple value to multiple attributes
    ;

}
