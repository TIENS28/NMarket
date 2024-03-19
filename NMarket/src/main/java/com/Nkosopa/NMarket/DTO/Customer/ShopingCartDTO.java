package com.Nkosopa.NMarket.DTO.Customer;

import com.Nkosopa.NMarket.DTO.Product.ProductDTO;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class ShopingCartDTO {

    private CustomerDTO customerDTO;

    private List<ProductDTO> productDTOS;

    private Long totalPrice;
}
