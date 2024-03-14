package com.Nkosopa.NMarket.DTO.Customer;

import com.Nkosopa.NMarket.DTO.BaseDTO;
import lombok.*;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class CustomerValueDTO extends BaseDTO<CustomerValueDTO> {
    private String attributeCode;

    private Long attributeId;

    private String value;

    private Long customerId;
}
