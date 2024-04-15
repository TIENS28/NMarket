package com.Nkosopa.NMarket.DTO.Customer;

import com.Nkosopa.NMarket.DTO.BaseDTO;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class CustomerLongValueDTO extends BaseDTO<CustomerLongValueDTO> {
    private Long value;
    private Long customerId;
    private Long attributeId;
}
