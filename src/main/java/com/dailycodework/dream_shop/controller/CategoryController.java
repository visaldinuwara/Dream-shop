package com.dailycodework.dream_shop.controller;

import java.util.List;

import com.dailycodework.dream_shop.model.Category;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.dailycodework.dream_shop.response.ApiResponse;

import com.dailycodework.dream_shop.service.category.ICategoryService;
import com.dailycodework.dream_shop.exceptions.AlreadyExistsException;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("${api.prefix}/Catogories")
public class CategoryController {
  private final ICategoryService categoryService;

  @GetMapping("/all")
  public ResponseEntity<ApiResponse> getAllCategories(){
    try{
    List<Category> catogories=categoryService.getAllCategories();
    return ResponseEntity.ok(new ApiResponse("Found!",catogories));
    }catch(Exception e){
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse("Error", HttpStatus.INTERNAL_SERVER_ERROR));
    }
  }
  @GetMapping("/add")
  public ResponseEntity<ApiResponse> addCatogory(@RequestBody Category name){
    try{
    Category theCatogory=categoryService.addCatogory(name);
    return ResponseEntity.ok(new ApiResponse("Success",theCatogory));
    }catch(AlreadyExistsException e){
      return ResponseEntity.status(HttpStatus.CONFLICT).body(new ApiResponse(e.getMessage(),null));
    }
  }
  @GetMapping("/category/{id}/category")
  public ResponseEntity<ApiResponse> getCategoryById(@PathVariable Long id){
    try{
      Category theCategory=categoryService.getCategoryById(id);
      return ResponseEntity.ok(new ApiResponse("Found", theCategory));
    }catch(Exception e){
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(e.getMessage(), null));  
    }
  }

    @GetMapping("/{name}/category")
  public ResponseEntity<ApiResponse> getCategoryByName(@PathVariable String name){
    try{
      Category theCategory=categoryService.getCategoryByName(name);
      return ResponseEntity.ok(new ApiResponse("Found", theCategory));
    }catch(Exception e){
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(e.getMessage(), null));  
    }
  }

      @GetMapping("/category/{id}/delete")
  public ResponseEntity<ApiResponse> deleteCatogory(@PathVariable Long id){
    try{
      categoryService.deleteCatogoryById(id);
      return ResponseEntity.ok(new ApiResponse("Found",null));
    }catch(Exception e){
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(e.getMessage(), null));  
    }
  }
}
