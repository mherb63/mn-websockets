package com.mherb.mnaut.domain;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
public class PriceUpdate {

    private String symbol;
    private BigDecimal price;
}
