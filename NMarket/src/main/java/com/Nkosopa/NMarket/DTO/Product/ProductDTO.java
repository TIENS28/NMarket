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

    private Long stock;

    private Double price;

    private String currency;

    private ProductTypeDTO productTypeDTO;

    private List<AttributeDTO> attributeDTOList;

}
