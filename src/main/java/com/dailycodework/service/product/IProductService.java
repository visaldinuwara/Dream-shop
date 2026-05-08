package com.dailycodework.service.product;

import java.util.List;

import com.dailycodework.model.Product;
import com.dailycodework.request.AddProductRequest;
import com.dailycodework.request.UpdateProductRequest;

public interface  IProductService {
  Product addProduct(AddProductRequest product);
  List<Product>getAllProducts();
  Product getProductById(Long id);
  void deleteProductById(Long id);
  Product updateProduct(UpdateProductRequest product, Long id);
  List<Product>getProductsByCategory(String category);
  List<Product>getProductsByBrand(String brand);
  List<Product>getProductsByPrandAndProduct(String category,String brand);
  List<Product>getProductsByName(String name);
  List<Product>getProductsByBrandAndName(String name,String brand);
  List <Product>getProductsByCategoryAndName(String category,String name);
  List<Product> getProductsByCategoryAndBrand(String category, String brand);
  List<Product> countByBrandAndName(String name, String brand);
}
