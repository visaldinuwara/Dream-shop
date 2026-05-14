package com.dailycodework.dream_shop.service.category;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.dailycodework.dream_shop.exceptions.AlreadyExistsException;
import com.dailycodework.dream_shop.exceptions.ResourceNotFoundException;
import com.dailycodework.dream_shop.model.Category;
import com.dailycodework.dream_shop.repository.CategoryRepo;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CategoryService implements ICategoryService{
  private final CategoryRepo categoryRepo;

    @Override
    public Category getCategoryById(Long id) {
      return categoryRepo.findById(id)
          .orElseThrow(()->new ResourceNotFoundException("Category not found!"));
    }

    @Override
    public Category getCategoryByName(String name) {
        return categoryRepo.findByName(name);
    }

    @Override
    public List<Category> getAllCategories() {
        return categoryRepo.findAll();
    }

    @Override
    public Category addCatogory(Category category) {
        return Optional.of(category).filter(c->!categoryRepo.existsByName(c.getName()))
            .map(categoryRepo::save)
            .orElseThrow(()->new AlreadyExistsException(category.getName()+"already exists"));
    }

    @Override
    public Category updateCatogory(Category category,Long id) {
        return Optional.ofNullable(getCategoryById(id))
                .map(oldCategory->{
                    oldCategory.setName(category.getName());
                    return categoryRepo.save(oldCategory);
                }).orElseThrow(()->new ResourceNotFoundException("Category not found!"));
    }


    @Override
    public void deleteCatogoryById(Long id) {
        categoryRepo.findById(id).ifPresentOrElse(categoryRepo::delete,()->{throw new ResourceNotFoundException("Category not found!");});
    }

  
}
