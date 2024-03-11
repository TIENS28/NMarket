package com.Nkosopa.NMarket.DTO.Customer;

import lombok.*;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class CustomerValueDTO {
    private String value;
//
//    private Long intValue;
//
//    private Date dateValue;

    private Long customerId;
}
