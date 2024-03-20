package com.Nkosopa.NMarket.DTO.Product;

import com.Nkosopa.NMarket.DTO.BaseDTO;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class ProductDTO extends BaseDTO<ProductDTO> {

    private String name;

    private String sku;

    private int stock;

    private ProductTypeDTO productTypeDTOS;

    private List<ProductAttributesDTO> attributesDTOS;

}
