package com.dailycodework.service.image;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.management.RuntimeErrorException;
import javax.sql.rowset.serial.SerialBlob;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.dailycodework.dto.ImageDto;
import com.dailycodework.exceptions.ResourceNotFoundException;
import com.dailycodework.model.Image;
import com.dailycodework.model.Product;
import com.dailycodework.repository.ImageRepo;
import com.dailycodework.service.product.IProductService;
import com.dailycodework.service.product.ProductService;

import jakarta.annotation.Resource;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ImageService implements IImageService{
  private final ImageRepo imageRepo;
  private final ProductService productService;

  @Override
  public Image getImageById(Long id) {
    return imageRepo.findById(id).orElseThrow(()->new ResourceNotFoundException("Image not found"));
  }

  @Override
  public void deleteImageById(Long id) {
    imageRepo.findById(id).ifPresentOrElse(imageRepo::delete,()->{throw new ResourceNotFoundException("Image not found with id:"+id);});
  }

  @Override
  public List<ImageDto> saveImage(List<MultipartFile> files, Long productId) {
      Product product=productService.getProductById(productId);
      List<ImageDto> savedImageDto=new ArrayList<>();
      for(MultipartFile file:files){
        try{
          Image image=new Image();
          image.setFileName(file.getOriginalFilename());
          image.setFileType(file.getContentType());
          image.setImage(new SerialBlob(file.getBytes()));
          image.setProduct(product);

          String downloadUrl="/api/v1/images/image/downlaod/";
          image.setDownloadUrl(downloadUrl+image.getId());

          Image savedImage=imageRepo.save(image);
          savedImage.setDownloadUrl(downloadUrl+savedImage.getId());
          imageRepo.save(savedImage);

          ImageDto imageDto=new ImageDto();
          imageDto.setId(savedImage.getId());
          imageDto.setImageName(savedImage.getFileName());
          imageDto.setDownloadUrl(savedImage.getDownloadUrl());
          savedImageDto.add(imageDto);
        }catch(IOException | SQLException e){
          throw new RuntimeException(e.getMessage());
        }
      }            
  }

  @Override
  public void updateImage(MultipartFile file, Long imageId) {
    Image image=getImageById(imageId);
    try{
    image.setFileName(file.getOriginalFilename());
    image.setFileType(file.getContentType());
    image.setImage(new SerialBlob(file.getBytes()));
    imageRepo.save(image);
    }catch (Exception e){
      throw new RuntimeException(e);
    }
  }
  
}
