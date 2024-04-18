package com.Nkosopa.NMarket.DTO.Other;

import com.Nkosopa.NMarket.DTO.Product.ProductDTO;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class CartProductDTO {
    public ProductDTO productDTO;
    public Long quantity;
}
