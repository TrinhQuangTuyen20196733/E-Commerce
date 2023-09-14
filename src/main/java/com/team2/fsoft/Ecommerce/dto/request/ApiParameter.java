package com.team2.fsoft.Ecommerce.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ApiParameter {
    public  int page =1 ;
    public  int limit;
    public  Filter filter;
}
