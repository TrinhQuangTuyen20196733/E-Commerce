package com.team2.fsoft.Ecommerce.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductDetailRequest {
    private String color;
    private String size;
    private BigDecimal originPrice;
    private BigDecimal price;
}
