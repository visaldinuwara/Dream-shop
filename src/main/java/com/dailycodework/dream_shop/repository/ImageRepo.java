package com.dailycodework.dream_shop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.dailycodework.dream_shop.model.Image;

@Repository
public interface ImageRepo extends JpaRepository<Image,Long>{
}
