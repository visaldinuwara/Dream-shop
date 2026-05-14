package com.dailycodework.dream_shop.service.category;

import java.util.List;

import com.dailycodework.dream_shop.model.Category;

public interface  ICategoryService {
  Category getCategoryById(Long id);
  Category getCategoryByName(String name);
  List<Category> getAllCategories();
  Category addCatogory(Category category);
  Category updateCatogory(Category category,Long id);
  void deleteCatogoryById(Long id);
}
