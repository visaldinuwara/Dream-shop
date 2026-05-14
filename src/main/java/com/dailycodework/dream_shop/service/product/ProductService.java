package com.dailycodework.dream_shop.service.product;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.dailycodework.dream_shop.exceptions.ProductNotFoundException;
import com.dailycodework.dream_shop.model.Category;
import com.dailycodework.dream_shop.model.Product;
import com.dailycodework.dream_shop.repository.CategoryRepo;
import com.dailycodework.dream_shop.repository.ProductRepo;
import com.dailycodework.dream_shop.request.AddProductRequest;
import com.dailycodework.dream_shop.request.UpdateProductRequest;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProductService implements IProductService {

    private final ProductRepo productRepo;
    private final CategoryRepo categoryRepo;

    @Override
    public Product addProduct(AddProductRequest addProductRequest) {
        // Check if the category is found in the DB
        // If Yes,set it as the new product category
        // If No,then save it as a new category
        // Then set as the new product category
        Category category = Optional.ofNullable(categoryRepo.findByName(addProductRequest.getCategory().getName()))
                .orElseGet(() -> {
                    Category newCategory = new Category(addProductRequest.getCategory().getName());
                    return categoryRepo.save(newCategory);
                });
        return null;
    }

    private Product createProduct(AddProductRequest appProductRequest, Category category) {
        return new Product(
                appProductRequest.getName(),
                appProductRequest.getBrand(),
                appProductRequest.getPrice(),
                appProductRequest.getInventory(),
                appProductRequest.getDescription(),
                category
        );
    }

    @Override
    public List<Product> getAllProducts() {
        return productRepo.findAll();
    }

    @Override
    public Product getProductById(Long id) {
        return productRepo.findById(id).orElseThrow(() -> new ProductNotFoundException("Product Not found"));
    }

    @Override
    public void deleteProductById(Long id) {
        productRepo.findById(id).ifPresentOrElse(productRepo::delete, () -> {
            throw new ProductNotFoundException("Product Not found");
        });
    }

    @Override
    public Product updateProduct(UpdateProductRequest request, Long id) {
        return productRepo.findById(id)
            .map(product -> updateExistingProduct(product, request))
            .map(productRepo::save)
            .orElseThrow(() -> new ProductNotFoundException("Product not found!"));
    }

    private Product updateExistingProduct(Product existingProduct, UpdateProductRequest request) {
        existingProduct.setName(request.getName());
        existingProduct.setBrand(request.getBrand());
        existingProduct.setPrice(request.getPrice());
        existingProduct.setInventory(request.getInventory());
        existingProduct.setDescription(request.getDescription());

        Category category=categoryRepo.findByName(request.getCategory().getName());
        existingProduct.setCategory(category);
        return existingProduct;
    }

    @Override
    public List<Product> getProductsByCategory(String category) {
        return productRepo.findByCategoryName(category);
    }

    @Override
    public List<Product> getProductsByBrand(String brand) {
        return productRepo.findByBrand(brand);
    }

    @Override
    public List<Product> getProductsByPrandAndProduct(String category, String brand) {
        return productRepo.findByCategoryAndBrand(category,brand);
    }

    @Override
    public List<Product> getProductsByName(String name) {
        return productRepo.findByName(name);
    }

    @Override
    public List<Product> getProductsByBrandAndName(String name, String brand) {
        return productRepo.findByBrandAndName(brand, name);
    }

    @Override
    public List<Product> getProductsByCategoryAndName(String category, String name) {
        return productRepo.findByCategoryAndName(category, name);
    }

    @Override
    public List<Product> getProductsByCategoryAndBrand(String category, String brand) {
        return productRepo.findByCategoryAndBrand(category, brand);
    }

    @Override
    public Long countByBrandAndName(String name, String brand) {
        return productRepo.countByBrandAndName(brand, name);
    }

}
