package com.example.explorecali_images.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Document(collection = "images")
@Data
public class Image {
    @Id
    private String id;

    private String fileName;
    private byte[] data;
}
