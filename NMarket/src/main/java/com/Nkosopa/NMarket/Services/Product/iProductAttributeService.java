package com.Nkosopa.NMarket.Services.Product;

import com.Nkosopa.NMarket.DTO.Product.ProductAttributesDTO;

import java.util.List;

public interface iProductAttributeService {
    void addProductAttribute(ProductAttributesDTO productAttributesDTO);

    void addAttributeToAllProduct(List<ProductAttributesDTO> productAttributeDTOList);

    void deleteSingleProductAttribute(Long productId, List<String> attributeCodes);

    void deleteAttributesOfAllProduct(List<String> attributeCodes);
}
