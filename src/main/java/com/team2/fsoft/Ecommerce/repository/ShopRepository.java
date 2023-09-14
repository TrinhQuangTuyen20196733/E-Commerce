package com.team2.fsoft.Ecommerce.repository;

import com.team2.fsoft.Ecommerce.entity.Shop;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface ShopRepository extends JpaRepository<Shop,Long> {

    @Query(value = "SELECT * FROM shop WHERE id= ?1", nativeQuery = true)
    Optional<Shop> findByUserId(long id);
}
