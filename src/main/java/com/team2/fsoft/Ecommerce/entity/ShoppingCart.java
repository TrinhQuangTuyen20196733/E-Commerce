package com.team2.fsoft.Ecommerce.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "shopping_cart")
public class ShoppingCart extends BaseEntity {
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;


    @OneToMany(mappedBy = "shoppingCart", fetch = FetchType.LAZY)
    List<CartItem> cartItemList;

    private int count = 0;
}
