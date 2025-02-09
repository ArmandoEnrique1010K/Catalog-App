package com.backend.electronic.services.products;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.backend.electronic.models.dto.ProductDetailDto;
import com.backend.electronic.models.dto.ProductsListDto;
import com.backend.electronic.models.dto.TechSheetDto;
import com.backend.electronic.models.dto.mapper.ProductDtoMapper;
import com.backend.electronic.models.entities.Brand;
import com.backend.electronic.models.entities.Category;
import com.backend.electronic.models.entities.Feature;
import com.backend.electronic.models.entities.FeatureValue;
import com.backend.electronic.models.entities.Image;
import com.backend.electronic.models.entities.Product;
import com.backend.electronic.models.entities.ProductFeature;
import com.backend.electronic.models.requests.ProductRequest;
import com.backend.electronic.repositories.BrandRepository;
import com.backend.electronic.repositories.CategoryRepository;
import com.backend.electronic.repositories.FeatureRepository;
import com.backend.electronic.repositories.FeatureValueRepository;
import com.backend.electronic.repositories.ImageRepository;
import com.backend.electronic.repositories.ProductFeatureRepository;
import com.backend.electronic.repositories.ProductRepository;
import com.backend.electronic.services.images.ImageService;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private BrandRepository brandRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ImageRepository imageRepository;

    @Autowired
    private FeatureRepository featureRepository;

    @Autowired
    private FeatureValueRepository featureValueRepository;
    @Autowired
    private ProductFeatureRepository productFeatureRepository;

    @Autowired
    private ProductDtoMapper productDtoMapper;

    @Autowired
    private ImageService imageService;

    // TODO: SIMPLIFICAR EL CODIGO DE ESTE ARCHIVO
    private static final Logger logger = LoggerFactory.getLogger(ProductServiceImpl.class);

    @Transactional(readOnly = true)
    @Override
    public List<ProductsListDto> findAll() {
        List<Product> products = productRepository.findAllProducts();
        return products.stream().map(
                productDtoMapper::toListDto)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    @Override
    public List<ProductsListDto> findAllByName(String name) {
        List<Product> products = productRepository.findAllProductsByName(name);
        return products.stream().map(
                productDtoMapper::toListDto)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    @Override
    public List<ProductsListDto> findAllByOffer() {
        List<Product> products = productRepository.findAllProductsByOffer();
        return products.stream().map(
                productDtoMapper::toListDto)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    @Override
    public List<ProductsListDto> findAllByCategoryId(Long id) {
        List<Product> products = productRepository.findAllProductsByCategoryId(id);
        return products.stream().map(
                productDtoMapper::toListDto)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    @Override
    public List<ProductsListDto> findAllByFilters(String name, Long idCategory, List<Long> idsBrands, Boolean offer) {
        List<Product> products = productRepository.findAllFilteredProducts(name, idCategory, idsBrands, offer);
        return products.stream().map(
                productDtoMapper::toListDto)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    @Override
    public List<ProductsListDto> findAllByFeatureValues(List<Long> featureValues) {
        List<Product> products = productRepository.findByFeatureValues(featureValues);
        return products.stream().map(
                productDtoMapper::toListDto)
                .collect(Collectors.toList());

    }

    // NO SE RECOMIENDA USAR LO SIGUIENTE EN FINDBYID

    // @Transactional(readOnly = true)
    // @Override
    // public Optional<ProductDetailDto> findById(Long id) {
    // return
    // Optional.ofNullable(productRepository.findById(id).map(productDtoMapper::toDetailDto).orElseThrow());
    // }

    @Transactional(readOnly = true)
    @Override
    public Optional<ProductDetailDto> findById(Long id) {
        Optional<Product> optionalProduct = productRepository.findById(id);
        if (optionalProduct.isPresent() && optionalProduct.get().getStatus() == true) {
            // Nota, solamente retornara el producto si el status esta en true
            // TODO: APLICAR ESTA TECNICA EN LAS DEMAS ENTITADES
            return optionalProduct.map(productDtoMapper::toDetailDto);
        } else {
            return Optional.empty();
        }
    }

    @Transactional
    @Override
    public ProductDetailDto save(Product product, MultipartFile file) {

        if (file == null || file.isEmpty()) {
            throw new IllegalArgumentException("La imagen del producto no puede estar vacía");
        }

        Brand brand = brandRepository.findById(product.getBrand().getId())
                .orElseThrow(() -> new IllegalArgumentException(
                        "La marca con ID " + product.getBrand().getId() + " no existe"));
        product.setBrand(brand);

        Category category = categoryRepository.findById(product.getCategory().getId())
                .orElseThrow(() -> new IllegalArgumentException(
                        "La categoría con ID " + product.getCategory().getId() + " no existe"));
        product.setCategory(category);

        // Asignar valores directos al producto (solamente los campos que no seran
        // llenados por el usuario)
        product.setStatus(true);
        product.setCreatedAt(LocalDateTime.now());
        product.setUpdatedAt(LocalDateTime.now());

        // 🔹 Primero guardamos el producto sin la imagen
        Product savedProduct = productRepository.save(product);

        // 🔹 Ahora guardamos la imagen, porque ya sabemos que el producto se guardó
        // bien
        String nameImage = imageService.storeImage(file);
        Image image = new Image();
        image.setName(nameImage);
        image = imageRepository.save(image);

        // 🔹 Ahora asignamos la imagen al producto y guardamos de nuevo
        savedProduct.setImage(image);
        productRepository.save(savedProduct); // Segunda actualización con imagen

        return productDtoMapper.toDetailDto(savedProduct);
    }

    @Transactional
    @Override
    public ProductDetailDto saveWithTechSheet(Product product, MultipartFile file) {

        if (file == null || file.isEmpty()) {
            throw new IllegalArgumentException("La imagen del producto no puede estar vacía");
        }

        Brand brand = brandRepository.findById(product.getBrand().getId())
                .orElseThrow(() -> new IllegalArgumentException(
                        "La marca con ID " + product.getBrand().getId() + " no existe"));
        product.setBrand(brand);

        Category category = categoryRepository.findById(product.getCategory().getId())
                .orElseThrow(() -> new IllegalArgumentException(
                        "La categoría con ID " + product.getCategory().getId() + " no existe"));
        product.setCategory(category);

        // Asignar valores directos al producto (solamente los campos que no seran
        // llenados por el usuario)
        product.setStatus(true);
        product.setCreatedAt(LocalDateTime.now());
        product.setUpdatedAt(LocalDateTime.now());

        // 🔹 Primero guardamos el producto sin la imagen
        Product savedProduct = productRepository.save(product);

        // 🔹 Ahora guardamos la imagen, porque ya sabemos que el producto se guardó
        // bien
        String nameImage = imageService.storeImage(file);
        Image image = new Image();
        image.setName(nameImage);
        image = imageRepository.save(image);

        // 🔹 Ahora asignamos la imagen al producto y guardamos de nuevo
        savedProduct.setImage(image);
        productRepository.save(savedProduct); // Segunda actualización con imagen

        // TODO: REPARAR ESTA PARTE
        // Guardar la ficha técnica
        if (product.getProductFeature() != null) {

            for (ProductFeature productFeature : product.getProductFeature()) {
                TechSheetDto entry = new TechSheetDto(productFeature.getFeature().getName(),
                        productFeature.getFeatureValue().getValue());
                Feature feature = featureRepository.findByName(entry.getFeature())
                        // Si no encuentra la caracteristica, lo debe guardar como una nueva
                        // caracteristica
                        .orElseGet(() -> featureRepository.save(new Feature(null, entry.getFeature(), true)));

                FeatureValue featureValue = featureValueRepository.findByFeatureAndValue(feature, entry.getValue())
                        .orElseGet(
                                () -> featureValueRepository
                                        .save(new FeatureValue(null, feature, entry.getValue(), null)));

                if (!productFeatureRepository.existsByProductAndFeatureValue(savedProduct, featureValue)) {
                    productFeatureRepository.save(new ProductFeature(null, savedProduct, featureValue, feature));
                }
            }
        }

        return productDtoMapper.toDetailDto(savedProduct);
    }

    @Transactional
    @Override
    public Optional<ProductDetailDto> update(ProductRequest product, MultipartFile file, Long id) {

        Optional<Product> optional = productRepository.findById(id);

        if (optional.isEmpty()) {
            return Optional.empty(); // Si el producto no existe, no hacemos nada
        }

        Product productDb = optional.get();

        // Validamos que la marca y la categoría existan antes de asignarlas
        Brand brand = brandRepository.findById(product.getBrand().getId())
                .orElseThrow(() -> new IllegalArgumentException(
                        "La marca con ID " + product.getBrand().getId() + " no existe"));
        productDb.setBrand(brand);

        Category category = categoryRepository.findById(product.getCategory().getId())
                .orElseThrow(() -> new IllegalArgumentException(
                        "La categoría con ID " + product.getCategory().getId() + " no existe"));
        productDb.setCategory(category);

        // Actualizamos los datos del producto
        productDb.setName(product.getName());
        productDb.setCode(product.getCode());
        productDb.setInOffer(product.getInOffer());
        productDb.setPrice(product.getPrice());

        if (productDb.getInOffer()) {
            productDb.setOfferPrice(product.getOfferPrice());
        } else {
            productDb.setOfferPrice(null);
        }

        productDb.setDescription(product.getDescription());
        productDb.setStatus(true);
        productDb.setUpdatedAt(LocalDateTime.now());

        // Primero guardamos el producto sin la imagen
        Product updatedProduct = productRepository.save(productDb);

        // Si se sube una imagen nueva, la reemplazamos
        if (file != null && !file.isEmpty()) {
            System.out.println("SUBIENDO UNA NUEVA IMAGEN...");
            String nameImage = imageService.storeImage(file);

            Image image = new Image();
            image.setName(nameImage);
            image = imageRepository.save(image);

            updatedProduct.setImage(image);
            productRepository.save(updatedProduct); // Segunda actualización con imagen
        } else {
            System.out.println("NO SE SUBIÓ UNA NUEVA IMAGEN, SE MANTIENE LA ACTUAL.");
        }

        return Optional.of(productDtoMapper.toDetailDto(updatedProduct));
    }

    @Override
    @Transactional
    public void disable(Long id) {
        Optional<Product> optional = productRepository.findById(id);

        if (optional.isPresent()) {
            Product productDb = optional.orElseThrow();
            productDb.setStatus(false);
            productRepository.save(productDb);
        }
    }

}
