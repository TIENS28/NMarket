package com.Nkosopa.NMarket.DTO.Customer;

import com.Nkosopa.NMarket.DTO.BaseDTO;
import com.Nkosopa.NMarket.DTO.Product.ProductDTO;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class CustomerOrderDTO extends BaseDTO<CustomerOrderDTO> {

    private Long customerId;

    private List<ProductDTO> productDTOList;
}
