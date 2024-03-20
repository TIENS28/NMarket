package com.Nkosopa.NMarket.DTO.Product;

import com.Nkosopa.NMarket.DTO.BaseDTO;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class ProductValueDTO extends BaseDTO<ProductValueDTO> {

    private Long attributeId;

    private String value;

    private Long productId;
}
