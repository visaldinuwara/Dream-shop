package com.dailycodework.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.web.multipart.MultipartFile;

import com.dailycodework.dto.ImageDto;
import com.dailycodework.model.Image;

public interface ImageRepo extends JpaRepository{
  Image findById(Long id);
  List<ImageDto> save(Image image);
}
