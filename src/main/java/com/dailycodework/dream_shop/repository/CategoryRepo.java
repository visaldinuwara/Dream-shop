package com.dailycodework.dream_shop.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.dailycodework.dream_shop.model.Category;
@Repository
public interface  CategoryRepo extends  JpaRepository<Category, Long>{
  Category findByName(String name);
  boolean existsByName(String name);
}
