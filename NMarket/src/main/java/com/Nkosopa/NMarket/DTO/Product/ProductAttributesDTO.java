package com.Nkosopa.NMarket.DTO.Product;

import com.Nkosopa.NMarket.Entity.DataType;
import com.Nkosopa.NMarket.Entity.Product.ProductDateValue;
import com.Nkosopa.NMarket.Entity.Product.ProductLongValue;
import com.Nkosopa.NMarket.Entity.Product.ProductTextValue;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class ProductAttributesDTO {

    private Long productId;

    private String entity_code;

    private String attribute_code;

    private String attribute_name;

    private Long type_id;

    private List<ProductTextValue> textValues;

    private List<ProductLongValue> intValues;

    private List<ProductDateValue> dateValues;

    private DataType dataType;

}
