package com.dailycodework.repository;
import org.springframework.data.jpa.repository.JpaRepository;

import com.dailycodework.model.Category;

public interface  CategoryRepo extends  JpaRepository<Category, Long>{

  Category findByName(String name);
  boolean existsByName(String name);
}
