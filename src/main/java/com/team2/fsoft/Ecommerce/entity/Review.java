package com.team2.fsoft.Ecommerce.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Review  extends  BaseEntity{

    private int rate;

    private String content;

    @ManyToOne
    @JoinColumn(name= "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name= "product_id")
    private Product product;




}
