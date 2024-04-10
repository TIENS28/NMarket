package com.Nkosopa.NMarket.DTO.Product;


import com.Nkosopa.NMarket.DTO.BaseDTO;
import com.Nkosopa.NMarket.Entity.DataType;
import lombok.*;

import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AttributeDTO extends BaseDTO {

    private String attributeName;

    private List<ProductTextValueDTO> textValues;

    private List<ProductLongValueDTO> intValues;

    private List<ProductDateValueDTO> dateValues;

    private DataType dataType;


}
