package com.Nkosopa.NMarket.DTO.Customer;

import lombok.*;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class CustomerValueDTO{

    private Long attributeId;

    private String value;

    private Long customerId;
}
