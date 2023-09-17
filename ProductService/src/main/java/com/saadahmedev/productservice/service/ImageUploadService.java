package com.saadahmedev.productservice.service;

import com.saadahmedev.productservice.entity.Image;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class ImageUploadService {

    @Value("${file.upload.directory}")
    private String imageUploadDirectory;

    public List<Image> uploadImage(List<MultipartFile> images) throws IOException {
        List<Image> imageList = new ArrayList<>();

        for (MultipartFile img : images) {
            String uniqueFileName = UUID.randomUUID() + "-" + img.getOriginalFilename();

            String imagePath = imageUploadDirectory + File.separator + uniqueFileName;
            File dest = new File(imagePath);

            if (!dest.exists()) dest.mkdirs();

            img.transferTo(dest);

            imageList.add(
                    Image.builder()
                            .url("http://localhost:8080/PRODUCT-SERVICE/product/media/images/" + uniqueFileName)
                            .build()
            );
        }

        return imageList;
    }
}