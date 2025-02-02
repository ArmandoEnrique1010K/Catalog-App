package com.backend.electronic.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.backend.electronic.services.images.ImageService;

@RestController
@RequestMapping("/images")
@CrossOrigin(originPatterns = "http://localhost:5173")
public class ImageController {

    @Autowired
    private ImageService imageService;

    // SOLAMENTE FUNCIONA CON POSTMAN, PUES THUNDERCLIENT NO SOPORTA EL MANEJO DE
    // IMAGENES EN EL RESPONSE
    @GetMapping("/{name}")
    public ResponseEntity<Resource> show(@PathVariable String name) {
        Resource resource = imageService.loadAsResource(name);
        String contentType = determineContentType(name);
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .body(resource);
    }

    // TODO: INVESTIGAR ESTO
    private String determineContentType(String name) {
        if (name.endsWith(".jpg") || name.endsWith(".jpeg")) {
            return MediaType.IMAGE_JPEG_VALUE;
        } else if (name.endsWith(".png")) {
            return MediaType.IMAGE_PNG_VALUE;
        } else if (name.endsWith(".gif")) {
            return MediaType.IMAGE_GIF_VALUE;
        } else {
            return MediaType.APPLICATION_OCTET_STREAM_VALUE;
        }
    }
}
