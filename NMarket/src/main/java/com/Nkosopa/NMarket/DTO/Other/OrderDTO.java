package com.Nkosopa.NMarket.DTO.Other;

import com.Nkosopa.NMarket.DTO.BaseDTO;
import com.Nkosopa.NMarket.DTO.Customer.CustomerDTO;
import com.Nkosopa.NMarket.DTO.Product.ProductDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class OrderDTO extends BaseDTO<OrderDTO> {
    private List<CartProductDTO> cartProductDTOS;
    private Long totalPrice;
    private LocalDateTime orderDate;
}
