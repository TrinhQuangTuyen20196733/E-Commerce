package com.team2.fsoft.Ecommerce.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserLog implements Serializable {
    public long id;
    public String module;
    public  String action;
    public  String email;
    public LocalDateTime createAt;
}
