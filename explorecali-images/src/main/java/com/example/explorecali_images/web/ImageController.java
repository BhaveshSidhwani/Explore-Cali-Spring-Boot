package com.example.explorecali_images.web;

import java.io.IOException;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.explorecali_images.business.ImageService;
import com.example.explorecali_images.model.IdName;
import com.example.explorecali_images.model.Image;

import lombok.extern.slf4j.Slf4j;


@RestController
@Slf4j
@RequestMapping(path = "/api/images")
public class ImageController {
    private final ImageService imageService;

    public ImageController(ImageService imageService) {
        this.imageService = imageService;
    }

    @PostMapping(path = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public String postMethodName(@RequestParam MultipartFile file) throws IOException {
        log.info("POST /api/images {}", file.getOriginalFilename());
        Image image = new Image();
        image.setFileName(file.getOriginalFilename());
        image.setData(file.getBytes());
        Image savedImage = imageService.saveImage(image);
        return savedImage.getId();
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<byte[]> handleFileDownload(@PathVariable String id) {
        log.info("GET /api/images/{}", id);
        return imageService
                .getImage(id)
                .map(i -> ResponseEntity.ok()
                        .header("Content-Disposition", String.format("attachment; filename=\"%s\"", i.getFileName()))
                        .body(i.getData()))
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping
    public List<IdName> findAll() {
        log.info("GET /api/images");
        return imageService.getIdNames();
    }
}
