package com.team2.fsoft.Ecommerce.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Filter {
    public Date created;
    public  String author;
    public  boolean ascending = false;
    public  String categoryCode;
    public String orderBy;
}

