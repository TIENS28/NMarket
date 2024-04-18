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
public class CustomerDateValueDTO extends BaseDTO<CustomerDateValueDTO> {
    private Date value;

}
