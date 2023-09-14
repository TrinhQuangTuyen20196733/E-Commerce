package com.team2.fsoft.Ecommerce.repository;

import com.team2.fsoft.Ecommerce.entity.ShoppingCart;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Optional;

public interface ShoppingCartRepository extends JpaRepository<ShoppingCart,Long> {
    @Query(value = "SELECT * FROM shopping_cart WHERE user_id= ?1", nativeQuery = true)
    Optional<ShoppingCart> findByUserId(Long userId);
}
