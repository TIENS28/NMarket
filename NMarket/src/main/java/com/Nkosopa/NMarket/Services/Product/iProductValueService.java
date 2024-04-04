package com.Nkosopa.NMarket.Services.Product;

import com.Nkosopa.NMarket.DTO.Product.ProductDTO;
import com.Nkosopa.NMarket.DTO.Product.ProductValueDTO;

import java.util.List;

public interface iProductValueService {

    void addValueToProductAttribute(Long productAttributeId, ProductValueDTO valueDTO)//add value to one attribute
    ;

    void addValuesToProductAttributes(List<ProductValueDTO> valueDTOs)//add multiple value to multiple attributes
    ;

    ProductDTO updateValueOfProductAttribute(Long productAttributeId, ProductValueDTO valueDTO)// update value of one attribute
    ;

    ProductDTO updateProductAttributeValues(List<ProductValueDTO> valueDTOs);
}
