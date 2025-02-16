package com.backend.electronic.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.apache.catalina.connector.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Sort;
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
import com.backend.electronic.models.dto.ProductDetailTechSheetDto;
import com.backend.electronic.models.dto.ProductsListDto;
import com.backend.electronic.models.dto.TechSheetDto;
import com.backend.electronic.models.entities.ProductImage;
import com.backend.electronic.models.entities.Product;
import com.backend.electronic.models.requests.ProductRequest;
import com.backend.electronic.services.ProductFeatureService;
import com.backend.electronic.services.ProductService;
import com.backend.electronic.validators.CustomValidator;

import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/products")
@CrossOrigin(originPatterns = "http://localhost:5173")
public class ProductController {

    @Autowired
    private ProductService productService;

    @Autowired
    private CustomValidator validationService;
    @Autowired
    private ProductFeatureService productFeatureService;

    private static final Logger logger = LoggerFactory.getLogger(ProductController.class);

    // ES NORMAL QUE EN LA CONSOLA SE MUESTRE VARIAS CONSULTAS A LA BASE DE DATOS
    // (SEGUN LA CANTIDAD DE PRODUCTOS)
    // TODO: DEBERIA DEVOLVER UN RESPONSEENTITY EN LUGAR DE UN LIST
    @GetMapping
    public List<ProductsListDto> listAll() {
        return productService.findAll();
    }

    @GetMapping("/offer")
    public List<ProductsListDto> listByOffer() {
        return productService.findAllByOffer();
    }

    @GetMapping("/searchByName")
    public List<ProductsListDto> searchListByName(@RequestParam("name") String name) {
        return productService.findAllByName(name);
    }

    @GetMapping("category/{id}")
    public List<ProductsListDto> listByCategory(@PathVariable Long id) {
        return productService.findAllByCategoryId(id);
    }

    @GetMapping("/search/filters")
    public List<ProductsListDto> listByFilters(
            @RequestParam(value = "name", required = false) String name,
            @RequestParam(value = "idCategory", required = false) Long idCategory,
            @RequestParam(value = "idsBrands", required = false) List<Long> idsBrands,
            @RequestParam(value = "offer", required = false) Boolean offer) {

        return productService.findAllByFilters(name, idCategory, idsBrands, offer);
    }

    // METODO DE PRUEBA PARA BUSCAR POR VALORES DE LAS CARACTERISTICA (UTIL PARA
    // FILTRAR PRODUCTOS POR SU CARACTERISTICA)
    @GetMapping("/search/features")
    public List<ProductsListDto> listByFeaturesValues(@RequestParam List<Long> featureValues) {
        return productService.findAllByFeatureValues(featureValues);
    }

    // @GetMapping("/{id}")
    // public ResponseEntity<?> showById(@PathVariable Long id) {

    // Optional<ProductDetailDto> product = productService.findById(id);

    // if (product.isPresent()) {
    // return ResponseEntity.ok(product.orElseThrow());
    // }

    // return ResponseEntity.notFound().build();
    // }

    // NOTA IMPORTANTE:
    // Para que funcione correctamente, todos los productos deben tener su ficha
    // tecnica (pues solamente muestra los productos que tienen su ficha tecnica)
    @GetMapping("/search")
    public ResponseEntity<List<ProductsListDto>> listFilteredProducts(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) Long idCategory,
            @RequestParam(required = false) List<Long> idsBrands,
            @RequestParam(required = false) Boolean offer,
            @RequestParam(required = false) Double minPrice,
            @RequestParam(required = false) Double maxPrice,
            @RequestParam(required = false) List<Long> featureValues,
            @RequestParam(defaultValue = "createdAt") String sortBy, // Orden predeterminado
            @RequestParam(defaultValue = "DESC") String order // Dirección predeterminada
    ) {

        Sort.Direction direction = Sort.Direction.fromString(order);

        // POSIBLES VALORES PARA sortBy:
        // - createdAt: fecha de creación (por defecto)
        // - name: nombre del producto
        // - price: precio del producto
        // - brand.name: nombre de marca
        Sort sort = Sort.by(direction, sortBy);

        List<ProductsListDto> products = productService.findAllBySevenFilters(name,
                idCategory, idsBrands, offer, minPrice, maxPrice, featureValues, sort);

        return ResponseEntity.ok(products);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> showById(@PathVariable Long id) {
        Optional<ProductDetailDto> product = productService.findById(id);

        if (product.isPresent()) {

            // Si el status es false, no lo debe mostrar

            return ResponseEntity.ok(product.get()); // Retorna 200 OK con el producto
        } else {
            return ResponseEntity.notFound().build(); // Retorna 404 Not Found
        }
    }

    // ENDPOINT PARA ENCONTRAR UN PRODUCTO INCLUYENDO SU FICHA TECNICA
    @GetMapping("/full/{id}")
    public ResponseEntity<?> showFullById(@PathVariable Long id) {
        Optional<ProductDetailTechSheetDto> product = productService.findFullProductById(id);

        if (product.isPresent()) {

            // Si el status es false, no lo debe mostrar

            return ResponseEntity.ok(product.get()); // Retorna 200 OK con el producto
        } else {
            return ResponseEntity.notFound().build(); // Retorna 404 Not Found
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

    // TODO: ELIMINAR ESTO
    // @PostMapping(value = "/createTest", consumes = {
    // MediaType.APPLICATION_JSON_VALUE,
    // MediaType.MULTIPART_FORM_DATA_VALUE })
    // public ResponseEntity<?> createTestWithTechSheet(
    // @Valid @RequestPart("product") Product product, BindingResult result,
    // @RequestPart(value = "image", required = false) MultipartFile image
    // // @RequestPart("techSheet") List<ProductFeature> techSheet
    // ) { // ✅ Nueva clave para ficha técnica

    // // Validaciones (si fallan, lanzarán excepciones y se detendrá el flujo)
    // validationService.validateFields(result);
    // validationService.validateImage(image);

    // try {
    // ProductDetailDto savedProduct = productService.saveWithTechSheet(product,
    // image);
    // return ResponseEntity.status(HttpStatus.CREATED).body(savedProduct);
    // } catch (DataIntegrityViolationException ex) {
    // // Solamente si hay un registro duplicado en uno de los campos, devolvera el
    // // error definido en GlobalExceptionHandler

    // throw ex;
    // } catch (Exception ex) {
    // // Manejar otros errores
    // return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
    // .body("Error al guardar el producto: " + ex.getMessage());
    // }

    // }

    // NOTA: POR PRIMERA VEZ SUPERE A CHATGPT, COPILOT Y DEEPSEEK, PORQUE LA
    // SOLUCIÓN ESTABA EN EL CONTROLADOR, NO EN EL SERVICIO

    @PostMapping(value = "/new", consumes = { MediaType.APPLICATION_JSON_VALUE,
            MediaType.MULTIPART_FORM_DATA_VALUE })
    public ResponseEntity<?> createWithTechSheet(
            @Valid @RequestPart("product") Product product, BindingResult result,
            @RequestPart(value = "image", required = false) MultipartFile image,
            @RequestPart("techSheet") List<TechSheetDto> techSheet
    // @RequestPart("techSheet") List<ProductFeature> techSheet
    ) { // ✅ Nueva clave para ficha técnica

        // Validaciones (si fallan, lanzarán excepciones y se detendrá el flujo)
        validationService.validateFields(result);
        validationService.validateImage(image);

        try {
            ProductDetailDto savedProduct = productService.save(product, image);

            // INTENTAR ESTO: 2 SERVICIOS EN UN CONTROLADOR
            Long idProductInDb = savedProduct.getId();

            productFeatureService.saveTechSheet(idProductInDb, techSheet);

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

    // @PostMapping("/{id}/feature/tech-sheet")
    // public ResponseEntity<String> saveTechSheet(@PathVariable Long id,
    // @RequestBody List<TechSheetDto> techSheet) {
    // productFeatureService.saveTechSheet(id, techSheet);
    // return ResponseEntity.ok("Ficha técnica guardada exitosamente.");

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
            product.setProductImage(new ProductImage());
            product.getProductImage().setFile(image);
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

    @PutMapping(value = "update/{id}", consumes = { MediaType.APPLICATION_JSON_VALUE,
            MediaType.MULTIPART_FORM_DATA_VALUE })

    public ResponseEntity<?> updateWithTechSheet(@Valid @RequestPart("product") ProductRequest product,
            BindingResult result,
            @RequestPart(value = "image", required = false) MultipartFile image,
            @RequestPart("techSheet") List<TechSheetDto> techSheet,
            @PathVariable Long id) {

        validationService.validateFields(result);

        if (image != null && !image.isEmpty()) {
            product.setProductImage(new ProductImage());
            product.getProductImage().setFile(image);
        }

        try {
            // 1️⃣ Actualiza el producto (sin la ficha técnica aún)
            Optional<ProductDetailTechSheetDto> updatedProduct = productService.update2(product, image, id);

            if (updatedProduct.isEmpty()) {
                return ResponseEntity.notFound().build();
            }

            // 2️⃣ Actualiza la ficha técnica
            productFeatureService.updateTechSheet(id, techSheet);

            // 3️⃣ Recuperar el producto con la ficha técnica actualizada
            Optional<ProductDetailTechSheetDto> updatedProductWithTechSheet = productService
                    .findFullProductById(id);

            return updatedProductWithTechSheet
                    .map(ResponseEntity::ok)
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

    // private ResponseEntity<?> validation(BindingResult result) {
    // Map<String, String> errors = new HashMap<>();
    // result.getFieldErrors().forEach(err -> {
    // errors.put(err.getField(), err.getDefaultMessage());
    // });
    // return ResponseEntity.badRequest().body(errors);
    // }

    // TODO: Tener en cuenta los siguientes endpoints

    // Obtener todos los productos (solamente habilitados)
    // Buscar productos por nombre
    // Buscar productos por categoria
    // Obtener un producto por su ID
    // Crear un producto
    // Editar un producto
    // Eliminar un producto (cambiar el state a false)

}
