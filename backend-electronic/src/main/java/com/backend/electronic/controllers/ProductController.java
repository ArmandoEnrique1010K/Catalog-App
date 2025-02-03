package com.backend.electronic.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.backend.electronic.models.dto.ProductDetailDto;
import com.backend.electronic.models.dto.ProductsListDto;
import com.backend.electronic.models.entities.Brand;
import com.backend.electronic.models.entities.Category;
import com.backend.electronic.models.entities.Image;
import com.backend.electronic.models.entities.Product;
import com.backend.electronic.models.requests.ProductRequest;
import com.backend.electronic.services.products.ProductService;
import com.backend.electronic.services.validations.ValidationService;
import com.backend.electronic.services.validations.ValidationServiceImpl;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/products")
@CrossOrigin(originPatterns = "http://localhost:5173")
public class ProductController {

    @Autowired
    private ProductService productService;

    @Autowired
    private ValidationService validationService;

    private static final Logger logger = LoggerFactory.getLogger(ProductController.class);

    // ES NORMAL QUE EN LA CONSOLA SE MUESTRE VARIAS CONSULTAS A LA BASE DE DATOS
    // (SEGUN LA CANTIDAD DE PRODUCTOS)
    @GetMapping
    public List<ProductsListDto> listAll() {
        return productService.findAll();
    }

    @GetMapping("/offer")
    public List<ProductsListDto> listByOffer() {
        return productService.findAllByOffer();
    }

    @GetMapping("/search")
    public List<ProductsListDto> searchListByName(@RequestParam("name") String name) {
        return productService.findAllByName(name);
    }

    @GetMapping("category/{id}")
    public List<ProductsListDto> listByCategory(@PathVariable Long id) {
        return productService.findAllByCategoryId(id);
    }

    @GetMapping("/search/filters")
    public List<ProductsListDto> listByFilters(@RequestParam("name") String name,
            @RequestParam("idCategory") Long idCategory, @RequestParam("idsBrands") List<Long> idBrands,
            @RequestParam("offer") Boolean offer) {
        return productService.findAllByFilters(name, idCategory, idBrands, offer);
    }

    // @GetMapping("/{id}")
    // public ResponseEntity<?> showById(@PathVariable Long id) {

    // Optional<ProductDetailDto> product = productService.findById(id);

    // if (product.isPresent()) {
    // return ResponseEntity.ok(product.orElseThrow());
    // }

    // return ResponseEntity.notFound().build();
    // }

    @GetMapping("/{id}")
    public ResponseEntity<?> showById(@PathVariable Long id) {
        Optional<ProductDetailDto> product = productService.findById(id);

        if (product.isPresent()) {
            return ResponseEntity.ok(product.get()); // Retorna 200 OK con el producto
        } else {
            return ResponseEntity.notFound().build(); // Retorna 404 Not Found
        }
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
    @PostMapping(consumes = { MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE })
    public ResponseEntity<?> create(
            @Valid @RequestPart("product") Product product, BindingResult result,
            @RequestPart(value = "image", required = false) MultipartFile image) {

        // Validaciones (si fallan, lanzarán excepciones y se detendrá el flujo)
        validationService.validateFields(result);
        validationService.validateImage(image);

        try {
            ProductDetailDto savedProduct = productService.save(product, image);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedProduct);
        } catch (DataIntegrityViolationException ex) {
            // Solamente si hay un registro duplicado en uno de los campos, devolvera el
            // error definido en GlobalExceptionHandler

            throw ex;
        } catch (Exception ex) {
            // Manejar otros errores
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al guardar el producto: " + ex.getMessage());
        }

    }

    // @PutMapping(value = "/test/{id}", consumes =
    // MediaType.MULTIPART_FORM_DATA_VALUE)
    // public ResponseEntity<?> updateTest(

    // @RequestParam("name") String name,
    // @RequestParam("code") String code,
    // @RequestParam("price") Double price,
    // @RequestParam("inOffer") Boolean inOffer,
    // @RequestParam(value = "offerPrice", required = false) Double offerPrice,
    // @RequestParam("description") String description,
    // @RequestParam("brandId") Long brandId,
    // @RequestParam("categoryId") Long categoryId,
    // @RequestParam("image") MultipartFile image,
    // @PathVariable Long id) {

    // // Crear el objeto Product con los datos recibidos
    // Product product = new Product();
    // product.setName(name);
    // product.setCode(code);
    // product.setPrice(price);
    // product.setInOffer(inOffer);
    // product.setOfferPrice(inOffer ? offerPrice : null);
    // product.setDescription(description);
    // // logger.info("Producto creado en memoria sin relaciones");

    // // Asignar la marca y la categoría
    // product.setBrand(new Brand());
    // product.getBrand().setId(brandId);
    // product.setCategory(new Category());
    // product.getCategory().setId(categoryId);

    // // logger.info("Marca y categoría asignadas");

    // // Asignar la imagen recibida
    // if (!image.isEmpty()) {
    // product.setImage(new Image());
    // product.getImage().setFile(image);
    // }

    // try {
    // Optional<ProductDetailDto> o = productService.update(product, image, id);

    // if (o.isPresent()) {
    // return ResponseEntity.status(HttpStatus.CREATED).body(o.orElseThrow());
    // } else {
    // return ResponseEntity.notFound().build();
    // }

    // // return ResponseEntity.status(HttpStatus.CREATED).body(o.orElseThrow());
    // } catch (Exception e) {
    // return ResponseEntity.notFound().build();
    // }

    // }

    // ESPECIFICA EN POSTMAN:
    // key - Value - Content-Type
    // product (text) - Texto de tipo JSON - application/json
    // image (file) - Una imagen (opcional) - image
    @PutMapping(value = "/{id}", consumes = { MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE })
    public ResponseEntity<?> update(
            @Valid @RequestPart("product") ProductRequest product, BindingResult result,
            @RequestPart(value = "image", required = false) MultipartFile image,
            @PathVariable Long id) {

        // validation(result);

        // if (result.hasErrors()) {
        // return validation(result);
        // }

        validationService.validateFields(result);

        if (image != null && !image.isEmpty()) {
            product.setImage(new Image());
            product.getImage().setFile(image);
        }

        try {
            Optional<ProductDetailDto> o = productService.update(product, image, id);
            return o.map(ResponseEntity::ok)
                    .orElseGet(() -> ResponseEntity.notFound().build());
        } catch (DataIntegrityViolationException ex) {
            // Violación de datos al insertar un registro duplicado
            throw ex;
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al actualizar el producto: " + e.getMessage());
        }
    }

    @PatchMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        Optional<ProductDetailDto> o = productService.findById(id);

        if (o.isPresent()) {
            productService.disable(id);
            return ResponseEntity.noContent().build(); // 204
        }
        return ResponseEntity.notFound().build(); // 404
    }

    private ResponseEntity<?> validation(BindingResult result) {
        Map<String, String> errors = new HashMap<>();
        result.getFieldErrors().forEach(err -> {
            errors.put(err.getField(), err.getDefaultMessage());
        });
        return ResponseEntity.badRequest().body(errors);
    }

    // TODO: Tener en cuenta los siguientes endpoints

    // Obtener todos los productos (solamente habilitados)
    // Buscar productos por nombre
    // Buscar productos por categoria
    // Obtener un producto por su ID
    // Crear un producto
    // Editar un producto
    // Eliminar un producto (cambiar el state a false)

}
