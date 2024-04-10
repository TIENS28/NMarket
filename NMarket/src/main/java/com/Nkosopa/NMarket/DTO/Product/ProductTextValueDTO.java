package com.Nkosopa.NMarket.DTO.Product;

import com.Nkosopa.NMarket.DTO.BaseDTO;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class ProductTextValueDTO extends BaseDTO<ProductTextValueDTO> {
    private String value;
    private Long productId;
    private Long attributeId;
}
