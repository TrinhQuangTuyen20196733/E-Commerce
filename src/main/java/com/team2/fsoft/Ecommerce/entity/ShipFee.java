package com.team2.fsoft.Ecommerce.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

import java.math.BigDecimal;

@Entity
@Table(name = "ship_fee")
public class ShipFee extends BaseEntity {

    private  String code;

    private  String name;

    @Column(name = "fee",precision = 8,scale = 2)
    private BigDecimal fee;

}
