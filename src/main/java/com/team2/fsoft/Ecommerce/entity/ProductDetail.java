package com.team2.fsoft.Ecommerce.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Entity
@Table(name = "product_detail")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductDetail extends  BaseEntity {

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    @ManyToOne
    @JoinColumn(name = "color_id")
    private Color color;

    @ManyToOne
    @JoinColumn(name = "size_id")
    private Size size;

    @Column(name = "origin_price",precision = 8,scale = 2)
    private BigDecimal originPrice;

    @Column(name = "price",precision = 8,scale = 2)
    private BigDecimal  price;

    @Column(name = "sold_quantity")
    private int soldQuantity;

    @Column(name = "in_stock")
    private  int inStock;

}
