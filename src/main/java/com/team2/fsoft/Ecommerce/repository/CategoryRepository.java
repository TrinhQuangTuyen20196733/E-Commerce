package com.team2.fsoft.Ecommerce.repository;

import com.team2.fsoft.Ecommerce.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category,Long> {
    @Query(value = "SELECT * FROM category WHERE name= ?1", nativeQuery = true)
    Optional<Category> findByName(String name);
}
