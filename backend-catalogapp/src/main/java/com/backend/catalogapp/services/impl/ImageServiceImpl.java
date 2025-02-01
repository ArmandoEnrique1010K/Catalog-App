package com.backend.catalogapp.services.impl;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.backend.catalogapp.repositories.ImageRepository;
import com.backend.catalogapp.services.ImageService;

import jakarta.annotation.PostConstruct;

@Service
public class ImageServiceImpl implements ImageService {

    // private static final Logger logger =
    // LoggerFactory.getLogger(ImageServiceImpl.class);

    @Value("${storage.location}")
    private String storageLocation;

    @Autowired
    private ImageRepository imageRepository;

    @PostConstruct
    @Override
    public void createFolder() {

        try {
            Files.createDirectories(Paths.get(storageLocation));
        } catch (IOException exception) {
            throw new RuntimeException("Error al crear el directorio de almacenamiento", exception);
        }

    }

    @Override
    @Transactional
    public String storeImage(MultipartFile image) {

        // Maneja las excepciones cuando no se sube imagen
        if (image == null || image.isEmpty()) {
            throw new IllegalArgumentException("El archivo es nulo o está vacío");
        }

        // Verifica que el tipo de contenido sea una imagen
        String contentType = image.getContentType();

        // Maneja la excepción si el tipo de contenido no es una imagen
        if (contentType == null || !contentType.startsWith("image")) {
            throw new IllegalArgumentException("El archivo no es una imagen válida");
        }
        try {

            // Obtiene los milisegundos transcurridos desde 01/01/1970 hasta la actualidad
            long miliseconds = System.currentTimeMillis();

            // Obtiene el nombre original de la imagen
            String originalNameImage = image.getOriginalFilename();

            // Obtiene la extensión de la imagen
            String fileExtension = originalNameImage.substring(originalNameImage.lastIndexOf(".") + 1);

            // Renombra el nombre de la imagen para evitar nombres repetidos
            String newNameImage = "Img_" + miliseconds + "." + fileExtension;

            // Copia la imagen al directorio de almacenamiento
            InputStream inputStream = image.getInputStream();
            Path path = Paths.get(storageLocation).resolve(newNameImage);

            // Copia el archivo de la imagen en el sistema de archivos
            Files.copy(inputStream, path, StandardCopyOption.REPLACE_EXISTING);

            // Devuelve el nombre establecido de la imagen almacenada
            return newNameImage;

        } catch (IOException exception) {
            throw new RuntimeException("Error al almacenar la imagen", exception);
        }
    }

    @Override
    @Transactional
    public Path loadImage(String name) {

        Path imagenPath = Paths.get(storageLocation).resolve(name);

        // Verifica si la imagen existe en el directorio
        if (!Files.exists(imagenPath)) {
            throw new IllegalArgumentException("La imagen no existe: " + name);
        }

        // Devuelve la ruta de la imagen
        return imagenPath;

    }

    @Transactional

    @Override
    public Resource loadAsResource(String name) { // Método para cargar una imagen como un recurso
        // Se puede acceder a la imagen de forma externa

        try {

            // Carga la imagen desde el directorio
            Path imagen = loadImage(name);

            // Crea un recurso URL a partir de la ruta de la imagen
            Resource resource = new UrlResource(imagen.toUri());

            // Verifica si el recurso existe y es legible (que se pueda leer)
            if (resource.exists() || resource.isReadable()) {
                return resource;
            } else {
                // Maneja el caso en que la imagen no es legible
                throw new IllegalArgumentException("La imagen no es legible: " + name);
            }

        } catch (MalformedURLException exception) {
            // Maneja el error en la conversión de la URI si llega a ocurrir
            // (Identificador de recursos uniforme)
            throw new RuntimeException("Error al cargar la imagen como recurso", exception);
        }

    }

    // @Override
    // public String getImageNameById(Long id) {
    // return imageRepository.findById(id).orElseThrow(() -> new
    // IllegalArgumentException("La imagen no existe"))
    // .getName();
    // }

}
