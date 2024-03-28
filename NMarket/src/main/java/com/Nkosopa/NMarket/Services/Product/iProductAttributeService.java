package com.Nkosopa.NMarket.Services.Product;

import com.Nkosopa.NMarket.DTO.Product.ProductAttributesDTO;

import java.util.List;

public interface iProductAttributeService {

    List<ProductAttributesDTO> addProductAttribute(ProductAttributesDTO productAttributesDTO);

    List<ProductAttributesDTO> addAttributeToAllProduct(List<ProductAttributesDTO> productAttributeDTOList);

    void deleteSingleProductAttribute(Long productId, List<String> attributeCodes);

    void deleteAttributesOfAllProduct(List<String> attributeCodes);
}
