package com.Nkosopa.NMarket.DTO.Other;

import com.Nkosopa.NMarket.DTO.BaseDTO;
import com.Nkosopa.NMarket.DTO.Customer.CustomerDTO;
import com.Nkosopa.NMarket.DTO.Product.ProductDTO;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class ShoppingCartDTO extends BaseDTO<ShoppingCartDTO> {

    private CustomerDTO customerDTO;

    private List<ProductDTO> productDTOS;

    private Long totalPrice;
}
