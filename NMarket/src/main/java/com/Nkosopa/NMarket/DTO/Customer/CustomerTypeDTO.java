package com.Nkosopa.NMarket.DTO.Customer;

import com.Nkosopa.NMarket.DTO.BaseDTO;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class CustomerTypeDTO extends BaseDTO<CustomerTypeDTO> {
    private String type;
}
