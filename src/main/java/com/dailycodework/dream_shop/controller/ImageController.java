package com.dailycodework.dream_shop.controller;

import java.util.List;

import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
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
import org.springframework.web.multipart.MultipartFile;

import com.dailycodework.dream_shop.dto.ImageDto;
import com.dailycodework.dream_shop.exceptions.ResourceNotFoundException;
import com.dailycodework.dream_shop.model.Image;
import com.dailycodework.dream_shop.response.ApiResponse;
import com.dailycodework.dream_shop.service.image.IImageService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("${api.prefix}/images")
public class ImageController {
  private final IImageService imageService;

  @PostMapping("/upload")
  public ResponseEntity<ApiResponse> saveImages(@RequestParam List<MultipartFile> files, @RequestParam Long productId) {
    try {
      List<ImageDto> imageDtos = imageService.saveImage(files, productId);
      return ResponseEntity.ok(new ApiResponse("Upload success!", imageDtos));
    } catch (Exception e) {
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
          .body(new ApiResponse("Upload Failed", e.getMessage()));
    }
  }

  @GetMapping("/image/download/{imageId}")
  public ResponseEntity<Resource> downloadImage(@PathVariable Long imageId) {
    Image image = imageService.getImageById(imageId);
    ByteArrayResource resource;
    try{
      resource = new ByteArrayResource(image.getImage().getBytes(1, (int) image.getImage().length()));
      return ResponseEntity.ok().contentType(MediaType.parseMediaType(image.getFileType()))
          .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + image.getFileName() + "\"")
          .body(resource);
    }catch(Exception e){
      return ResponseEntity.internalServerError().build();
    }
    }

  @DeleteMapping("/image/{imageId}/delete")
  public ResponseEntity<ApiResponse> deleteImage(@PathVariable Long imageId) {
    try {
      Image image = imageService.getImageById(imageId);
      if (image != null) {
        imageService.deleteImageById(imageId);
        return ResponseEntity.ok(new ApiResponse("Delete success!", null));
      }
      return ResponseEntity.ok(new ApiResponse("Failed!", null));
    } catch (ResourceNotFoundException e) {
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
          .body(new ApiResponse(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR));
    }
  }

  @PutMapping("/image/{imageId}/update")
  public ResponseEntity<ApiResponse> updateImage(@PathVariable Long imageId, @RequestBody MultipartFile file) {
    try {
      Image image = imageService.getImageById(imageId);
      if (image != null) {
        imageService.updateImage(file, imageId);
        return ResponseEntity.ok(new ApiResponse("Update success!", null));
      }
      return ResponseEntity.ok(new ApiResponse("Failed!", null));
    } catch (ResourceNotFoundException e) {
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
          .body(new ApiResponse(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR));
    }
  }

}
