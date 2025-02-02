package com.backend.electronic.services;

import java.nio.file.Path;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

public interface ImageService {

    public void createFolder();

    public String storeImage(MultipartFile image);

    public Path loadImage(String name);

    public Resource loadAsResource(String name);
}
