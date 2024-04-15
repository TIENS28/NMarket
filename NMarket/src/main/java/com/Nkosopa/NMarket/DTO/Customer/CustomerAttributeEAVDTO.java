package com.Nkosopa.NMarket.DTO.Customer;

import com.Nkosopa.NMarket.DTO.BaseDTO;
import com.Nkosopa.NMarket.Entity.DataType;
import lombok.*;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class CustomerAttributeEAVDTO extends BaseDTO {

    private String attributeName;

    private List<CustomerTextValueDTO> textValues;

    private List<CustomerLongValueDTO> longValues;

    private List<CustomerDateValueDTO> dateValues;

    private DataType dataType;

}
