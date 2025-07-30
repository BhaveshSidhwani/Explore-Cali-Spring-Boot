package com.example.explorecali_images.business;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.example.explorecali_images.model.IdName;
import com.example.explorecali_images.model.Image;
import com.example.explorecali_images.repo.ImageRepository;

@Service
public class ImageService {
    private final ImageRepository imageRepository;
    
    public ImageService(ImageRepository imageRepository) {
        this.imageRepository = imageRepository;
    }

    public Image saveImage(Image image) {
        return imageRepository.save(image);
    }

    public Optional<Image> getImage(String id) {
        return imageRepository.findById(id);
    }

    public Optional<Image> getByName(String filename) {
        return imageRepository.findByFileName(filename);
    }

    public List<IdName> getIdNames() {
        return imageRepository.findIdNameBy();
    }
}
