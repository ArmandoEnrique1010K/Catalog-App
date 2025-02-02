package com.backend.catalogapp.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.backend.catalogapp.models.dto.ProductDetailDto;
import com.backend.catalogapp.models.dto.ProductsListDto;
import com.backend.catalogapp.models.entities.Brand;
import com.backend.catalogapp.models.entities.Category;
import com.backend.catalogapp.models.entities.Image;
import com.backend.catalogapp.models.entities.Product;
import com.backend.catalogapp.services.ProductService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Valid;
import jakarta.validation.Validation;

@RestController
@RequestMapping("/products")
@CrossOrigin(originPatterns = "http://localhost:5173")
public class ProductController {

    @Autowired
    private ProductService productService;

    // @Autowired
    // private ImageService imageService;

    // @Autowired
    // private ImageRepository imageRepository;

    private static final Logger logger = LoggerFactory.getLogger(ProductController.class);

    // ES NORMAL QUE EN LA CONSOLA SE MUESTRE VARIAS CONSULTAS A LA BASE DE DATOS
    // (SEGUN LA CANTIDAD DE PRODUCTOS)
    @GetMapping
    public List<ProductsListDto> list() {
        return productService.findAllByStatusTrue();
    }

    // TODO: ENDPOINT PARA BUSCAR UN PRODUCTO POR SU NOMBRE (COMO PARAMETRO)
    @GetMapping("/search")
    public List<ProductsListDto> findByName(@RequestParam("name") String name) {
        return productService.findAllByName(name);
    }

    @GetMapping("/{id}")
    public ProductDetailDto findById(@PathVariable Long id) {
        return productService.findById(id).orElseThrow();
    }

    // TODO: USAR UN ModelAttribute Y RequestParam por separado, uno para el JSON
    @PostMapping(value = "/test", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> createTest(
            @RequestParam("name") String name,
            @RequestParam("code") String code,
            @RequestParam("price") Double price,
            @RequestParam("inOffer") Boolean inOffer,
            @RequestParam(value = "offerPrice", required = false) Double offerPrice,
            @RequestParam("description") String description,
            @RequestParam("brandId") Long brandId,
            @RequestParam("categoryId") Long categoryId,
            @RequestParam("image") MultipartFile image) {

        // logger.info("Inicio de creación de producto");

        // logger.debug("Datos recibidos: name={}, code={}, price={}, inOffer={},
        // offerPrice={}, description={}, brandId={}, categoryId={}, image={}",
        // name, code, price, inOffer, offerPrice, description, brandId, categoryId,
        // image.getOriginalFilename());

        if (image.isEmpty()) {
            // logger.warn("Imagen vacía recibida");
            return ResponseEntity.badRequest().body("Debe proporcionar una imagen válida.");
        }

        // Crear el objeto Product con los datos recibidos
        Product product = new Product();
        product.setName(name);
        product.setCode(code);
        product.setPrice(price);
        product.setInOffer(inOffer);
        product.setOfferPrice(inOffer ? offerPrice : null);
        product.setDescription(description);
        // logger.info("Producto creado en memoria sin relaciones");

        // Asignar la marca y la categoría
        product.setBrand(new Brand());
        product.getBrand().setId(brandId);
        product.setCategory(new Category());
        product.getCategory().setId(categoryId);

        // logger.info("Marca y categoría asignadas");

        // Asignar la imagen recibida
        product.setImage(new Image());
        product.getImage().setFile(image);

        // logger.info("Imagen asignada al producto: {}",
        image.getOriginalFilename();

        try {
            ProductDetailDto savedProduct = productService.save(product, image);
            // logger.info("Producto guardado correctamente con ID: {}",
            // savedProduct.getId());
            return ResponseEntity.status(HttpStatus.CREATED).body(savedProduct);
        } catch (Exception e) {
            // logger.error("Error al guardar el producto: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Error al guardar el producto: " + e.getMessage());
        }

    }

    // TODO: USAR UN ModelAttribute Y RequestParam por separado, uno para el JSON
    // REPARAR ESTE ENDPOINT
    @PostMapping(consumes = { MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE })
    public ResponseEntity<?> create(
            @RequestPart("product") @Valid String productJson, BindingResult result,
            @RequestPart("image") MultipartFile image) {

        if (image.isEmpty()) {
            return ResponseEntity.badRequest().body("Debe proporcionar una imagen válida.");
        }

        // Convertir JSON a objeto Product
        ObjectMapper objectMapper = new ObjectMapper();
        Product product;
        // TODO: INVESTIGAR COMO MEJORAR Y OBTENER EL MENSAJE DE ERROR
        try {
            product = objectMapper.readValue(productJson, Product.class);
        } catch (JsonProcessingException e) {
            return ResponseEntity.badRequest().body("Error al procesar JSON: " + e.getMessage());
        }

        if (result.hasErrors()) {
            System.out.println("Errores de validación: " + validation(result).getBody());
            return validation(result);
        }

        // Asignar la imagen manualmente
        Image img = new Image();
        img.setFile(image);
        product.setImage(img);

        try {
            ProductDetailDto savedProduct = productService.save(product, image);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedProduct);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(validation(result));
            // return ResponseEntity.status(HttpStatus.BAD_REQUEST)
            // .body("Error al guardar el producto: " + e.getMessage());
        }

    }

    @PutMapping(value = "/test/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> updateTest(

            @RequestParam("name") String name,
            @RequestParam("code") String code,
            @RequestParam("price") Double price,
            @RequestParam("inOffer") Boolean inOffer,
            @RequestParam(value = "offerPrice", required = false) Double offerPrice,
            @RequestParam("description") String description,
            @RequestParam("brandId") Long brandId,
            @RequestParam("categoryId") Long categoryId,
            @RequestParam("image") MultipartFile image,
            @PathVariable Long id) {

        // Crear el objeto Product con los datos recibidos
        Product product = new Product();
        product.setName(name);
        product.setCode(code);
        product.setPrice(price);
        product.setInOffer(inOffer);
        product.setOfferPrice(inOffer ? offerPrice : null);
        product.setDescription(description);
        // logger.info("Producto creado en memoria sin relaciones");

        // Asignar la marca y la categoría
        product.setBrand(new Brand());
        product.getBrand().setId(brandId);
        product.setCategory(new Category());
        product.getCategory().setId(categoryId);

        // logger.info("Marca y categoría asignadas");

        // Asignar la imagen recibida
        if (!image.isEmpty()) {
            product.setImage(new Image());
            product.getImage().setFile(image);
        }

        try {
            Optional<ProductDetailDto> o = productService.update(product, image, id);

            if (o.isPresent()) {
                return ResponseEntity.status(HttpStatus.CREATED).body(o.orElseThrow());
            } else {
                return ResponseEntity.notFound().build();
            }

            // return ResponseEntity.status(HttpStatus.CREATED).body(o.orElseThrow());
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }

    }

    @PutMapping(value = "/{id}", consumes = { MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE })
    public ResponseEntity<?> update(
            @RequestPart("product") String productJson, BindingResult result,
            @RequestPart(value = "image", required = false) MultipartFile image,
            @PathVariable Long id) {

        // Convertir JSON a objeto Product
        ObjectMapper objectMapper = new ObjectMapper();
        Product product;
        try {
            product = objectMapper.readValue(productJson, Product.class);
        } catch (JsonProcessingException e) {
            return ResponseEntity.badRequest().body("Error al procesar JSON: " + e.getMessage());
        }

        // VALIDACIÓN MANUAL de campos

        // Set<ConstraintViolation<Product>> violations =
        // Validation.buildDefaultValidatorFactory()
        // .getValidator().validate(product);

        // if (!violations.isEmpty()) {
        // List<String> errorMessages = violations.stream()
        // .map(violation -> violation.getPropertyPath() + " " + violation.getMessage())
        // .collect(Collectors.toList());
        // return ResponseEntity.badRequest().body(errorMessages);
        // }

        if (result.hasErrors()) {
            System.out.println("Errores de validación: " + validation(result).getBody());
            return validation(result);
        }

        if (image != null && !image.isEmpty()) {
            product.setImage(new Image());
            product.getImage().setFile(image);
        }

        try {
            Optional<ProductDetailDto> o = productService.update(product, image, id);
            return o.map(ResponseEntity::ok)
                    .orElseGet(() -> ResponseEntity.notFound().build());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al actualizar el producto: " + e.getMessage());
        }
    }

    private ResponseEntity<?> validation(BindingResult result) {
        Map<String, String> errors = new HashMap<>();

        result.getFieldErrors().forEach(err -> {
            errors.put(err.getField(), "El campo " + err.getField() + " " + err.getDefaultMessage());
        });
        return ResponseEntity.badRequest().body(errors);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<?> remove(@PathVariable Long id) {
        Optional<ProductDetailDto> o = productService.findById(id);

        if (o.isPresent()) {
            productService.remove(id);
            return ResponseEntity.noContent().build(); // 204
        }
        return ResponseEntity.notFound().build(); // 404
    }

    // TODO: Tener en cuenta los siguientes endpoints

    // Obtener todos los productos (solamente habilitados)

    // Obtener un producto por su ID
    // Crear un producto
    // Editar un producto
    // Eliminar un producto (cambiar el state a false)

    // Buscar productos por nombre
    // Buscar productos por categoria

}
