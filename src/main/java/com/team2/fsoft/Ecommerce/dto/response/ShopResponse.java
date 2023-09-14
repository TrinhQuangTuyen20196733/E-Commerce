package com.team2.fsoft.Ecommerce.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ShopResponse {
    public Long id;
    public String name;
    public String address;
    public String phoneNumber;
    public String avatarUrl;
    private String author;
    private String editor;
}
