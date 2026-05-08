package com.dailycodework.service.image;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.dailycodework.dto.ImageDto;
import com.dailycodework.model.Image;

public interface IImageService {
  Image getImageById(Long id);
  void deleteImageById(Long id);
  List<ImageDto> saveImage(List<MultipartFile> file, Long productId);
  void updateImage(MultipartFile file,Long imageId);
  
}
