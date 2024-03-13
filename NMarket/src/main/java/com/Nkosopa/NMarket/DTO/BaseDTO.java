package com.Nkosopa.NMarket.DTO;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class BaseDTO<T> {

    protected Long id;

}
