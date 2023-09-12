package com.team2.fsoft.Ecommerce.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "cart_item")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CartItem extends BaseEntity {

    @Min(0)
    private int amount;

    @ManyToOne
    @JoinColumn(name = "user_id" )
    private User user;

    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order order;

}
