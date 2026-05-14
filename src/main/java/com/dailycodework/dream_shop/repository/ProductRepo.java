package com.dailycodework.dream_shop.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.dailycodework.dream_shop.model.Product;

@Repository
public interface ProductRepo extends JpaRepository<Product, Long> {

    List<Product> findByCategoryName(String category);

    List<Product> findByBrand(String brand);

    List<Product> findByName(String name);

    List<Product> findByBrandAndName(String brand, String name);

    List<Product> findByCategoryAndName(String category, String name);

    List<Product> findByCategoryAndBrand(String category, String brand);

    List<Product> findProductsByCategory(String category);

    Long countByBrandAndName(String name, String brand);

}
