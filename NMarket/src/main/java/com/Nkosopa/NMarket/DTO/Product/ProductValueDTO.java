package com.Nkosopa.NMarket.DTO.Product;

import com.Nkosopa.NMarket.DTO.BaseDTO;
import com.Nkosopa.NMarket.Entity.Product.Product;
import lombok.*;

import java.util.List;

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
