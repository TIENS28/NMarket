package com.Nkosopa.NMarket.DTO.Customer;

import com.Nkosopa.NMarket.Entity.DataType;
import com.querydsl.core.Tuple;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class CustomerAttributeDTO {

    private String attribute_code;

    private String attribute_name;

    private List<CustomerTextValueDTO> textValues;

    private List<CustomerLongValueDTO> intValues;

    private List<CustomerDateValueDTO> dateValues;

    private DataType dataType;

    private Long customer_id;
}
