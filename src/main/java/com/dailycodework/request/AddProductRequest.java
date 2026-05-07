package com.dailycodework.request;

import java.math.BigDecimal;

import com.dailycodework.model.Category;

import lombok.Data;
@Data
public class AddProductRequest {
  private int id;
  private String name;
  private String brand;
  private BigDecimal price;
  private int inventory;
  private String description;
  private Category category;
}
