package com.Nkosopa.NMarket.DTO.Customer;

import com.Nkosopa.NMarket.DTO.BaseDTO;
import com.Nkosopa.NMarket.Entity.DataType;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class CustomerAttributeDTO extends BaseDTO<CustomerAttributeDTO> {

    private String attributeCode;

    private String attributeName;

    private List<CustomerTextValueDTO> textValues;

    private List<CustomerLongValueDTO> intValues;

    private List<CustomerDateValueDTO> dateValues;

    private DataType dataType;

    private Long customerId;
}
