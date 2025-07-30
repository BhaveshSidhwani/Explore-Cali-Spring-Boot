package com.example.explorecali_images.repo;

import java.util.List;
import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.example.explorecali_images.model.IdName;
import com.example.explorecali_images.model.Image;

public interface ImageRepository extends MongoRepository<Image, String> {
    Optional<Image> findByFileName(String name);
    
    List<IdName> findIdNameBy();
}
