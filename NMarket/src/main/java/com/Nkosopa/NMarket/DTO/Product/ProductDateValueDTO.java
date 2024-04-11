package com.Nkosopa.NMarket.DTO.Product;

import com.Nkosopa.NMarket.DTO.BaseDTO;
import lombok.*;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Data
public class ProductDateValueDTO extends BaseDTO<ProductDateValueDTO> {
    private Date value;
    private Long productId;
    private Long attributeId;
}
