package com.team2.fsoft.Ecommerce.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductRequest {
    public int id;
    public String name;
    public String description;
    public String category;
}
