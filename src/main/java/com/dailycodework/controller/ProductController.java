package com.dailycodework.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.service.annotation.PatchExchange;

import com.dailycodework.exceptions.ResourceNotFoundException;
import com.dailycodework.model.Product;
import com.dailycodework.request.AddProductRequest;
import com.dailycodework.request.UpdateProductRequest;
import com.dailycodework.response.ApiResponse;
import com.dailycodework.service.product.IProductService;
import com.mysql.cj.jdbc.result.UpdatableResultSet;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("${api.prefix}/Product")
public class ProductController {
    private final IProductService productService;

    @GetMapping("/all")
    public ResponseEntity<ApiResponse> getAllProducts(){
      List<Product> products=productService.getAllProducts();
      return ResponseEntity.ok(new ApiResponse("Success", products));
    }

    @GetMapping("product/{productId}/product")
    public ResponseEntity<ApiResponse> getProductById(@PathVariable Long productId){
      try{
      Product product=productService.getProductById(productId);
      return ResponseEntity.ok(new ApiResponse("Success", product));
      }catch(ResourceNotFoundException e){
        return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
      }
    }

    @PostMapping("/add")
    public ResponseEntity<ApiResponse> addProduct(@RequestBody AddProductRequest product){
      try{
        Product theProduct=productService.addProduct(product);
        return ResponseEntity.ok(new ApiResponse("Add product success", theProduct));
      }catch(Exception e){
        return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponse(e.getMessage(), null));
      }
    }

    @PutMapping("/products/{productId}/update")
    public ResponseEntity<ApiResponse> updateProduct(@RequestBody UpdateProductRequest request,@PathVariable Long productId){
      try{  
      Product theProduct=productService.updateProduct(request, productId);
        return ResponseEntity.ok(new ApiResponse("Update product success", theProduct));
      }catch(ResourceNotFoundException e){
        return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
      }
    }
    @DeleteMapping("/product/{productId}/delete")
    public ResponseEntity<ApiResponse> deleteProduct(@PathVariable Long productId){
      try{
      productService.deleteProductById(productId);
      return ResponseEntity.ok(new ApiResponse("Delete product success!", productId));
      }catch(ResourceNotFoundException e){
        return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
      }

    }

    @GetMapping("/products/by/brand-and-name/")
    public ResponseEntity<ApiResponse> getProductNameByName(@RequestParam String brandName,@RequestParam String productName){
      try{
      List<Product> products=productService.getProductsByBrandAndName(productName, brandName);
      if(products.isEmpty()){
        return ResponseEntity.status(NOT_FOUND).body(new ApiResponse("Not found", null));
      }
      return ResponseEntity.ok(new ApiResponse("success", products));
    }catch(Exception e){
        return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponse(e.getMessage(), null));
    }
  }

    @GetMapping("/products/by/category-and-brand/")
    public ResponseEntity<ApiResponse> getProductsByCategoryAndBrand(@RequestParam String category,@RequestParam String productName){
      try{
      List<Product> products=productService.getProductsByCategoryAndName(category,productName);
      if(products.isEmpty()){
        return ResponseEntity.status(NOT_FOUND).body(new ApiResponse("Not found", null));
      }
      return ResponseEntity.ok(new ApiResponse("success", products));
    }catch(Exception e){
        return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponse(e.getMessage(), null));
    }
  }

      @GetMapping("/products/by-name/{productName}")
    public ResponseEntity<ApiResponse> getProductsByCategoryAndBrand(@PathVariable String productName){
      try{
      List<Product> products=productService.getProductsByName(productName);
      if(products.isEmpty()){
        return ResponseEntity.status(NOT_FOUND).body(new ApiResponse("Not found", null));
      }
      return ResponseEntity.ok(new ApiResponse("success", products));
    }catch(Exception e){
        return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponse(e.getMessage(), null));
    }
  }

      @GetMapping("/products/by-brand/{brandName}")
    public ResponseEntity<ApiResponse> getProductsByBrand(@PathVariable String brandName){
      try{
      List<Product> products=productService.getProductsByBrand(brandName);
      if(products.isEmpty()){
        return ResponseEntity.status(NOT_FOUND).body(new ApiResponse("Not found", null));
      }
      return ResponseEntity.ok(new ApiResponse("success", products));
    }catch(Exception e){
        return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponse(e.getMessage(), null));
    }
  }

        @GetMapping("/products/{category}/all/products")
    public ResponseEntity<ApiResponse> getProductsByCategory(@PathVariable String category){
      try{
      List<Product> products=productService.getProductsByCategory(category);
      if(products.isEmpty()){
        return ResponseEntity.status(NOT_FOUND).body(new ApiResponse("Not found", null));
      }
      return ResponseEntity.ok(new ApiResponse("success", products));
    }catch(Exception e){
        return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponse(e.getMessage(), null));
    }
  }
  
}
