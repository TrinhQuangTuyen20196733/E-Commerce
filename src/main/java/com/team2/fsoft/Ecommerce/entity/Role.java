package com.team2.fsoft.Ecommerce.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table
@Data
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  String code;

    private  String name;
}
