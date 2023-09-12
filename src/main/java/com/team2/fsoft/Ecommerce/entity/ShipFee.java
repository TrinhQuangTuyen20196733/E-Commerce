package com.team2.fsoft.Ecommerce.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;

import java.math.BigDecimal;

@Entity
@Table(name = "ship_fee")
public class ShipFee {
    @Id
    private String code;
    @NotBlank
    private String name;

    @Column(name = "fee", precision = 8, scale = 2)
    private BigDecimal fee;

}
