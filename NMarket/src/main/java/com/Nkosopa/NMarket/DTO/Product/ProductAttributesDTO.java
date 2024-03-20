package com.Nkosopa.NMarket.DTO.Product;

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
public class ProductAttributesDTO extends BaseDTO<ProductAttributesDTO> {

    private Long productId;

    private String attributeCode;

    private String attributeName;

    private Long typeId;

    private List<ProductTextValueDTO> textValues;

    private List<ProductLongValueDTO> intValues;

    private List<ProductDateValueDTO> dateValues;

    private DataType dataType;

}
