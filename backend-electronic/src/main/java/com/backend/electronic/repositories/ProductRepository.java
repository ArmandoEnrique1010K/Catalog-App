package com.backend.electronic.repositories;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.backend.electronic.models.entities.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {

        // CONSULTAS NECESARIAS (Se consideran productos habilitados ):
        // - Obtener todos los productos
        // - " " " " por su nombre
        // - " " " " en oferta
        // - " " " " por su categoria

        // El uso de JOIN FETCH acelera la consulta a la base de datos
        // @Query("SELECT p FROM Product p JOIN FETCH p.brand JOIN FETCH p.category
        // WHERE p.status = true")

        // @Query("SELECT p FROM Product p JOIN FETCH p.brand JOIN FETCH p.category JOIN
        // FETCH p.image WHERE p.status = true AND p.brand.status = true AND
        // p.category.status = true")
        // List<Product> findAllProducts();

        // @Query("SELECT p FROM Product p JOIN FETCH p.brand JOIN FETCH p.category JOIN
        // FETCH p.image WHERE p.status = true AND p.brand.status = true AND
        // p.category.status = true AND p.name LIKE %:name%")
        // List<Product> findAllProductsByName(@Param("name") String name);

        // @Query("SELECT p FROM Product p JOIN FETCH p.brand JOIN FETCH p.category JOIN
        // FETCH p.image WHERE p.status = true AND p.brand.status = true AND
        // p.category.status = true AND p.inOffer = true")
        // List<Product> findAllProductsByOffer();

        // @Query("SELECT p FROM Product p JOIN FETCH p.brand JOIN FETCH p.category JOIN
        // FETCH p.image WHERE p.status = true AND p.brand.status = true AND
        // p.category.status = true AND p.category.id = :id")
        // List<Product> findAllProductsByCategoryId(@Param("id") Long id);

        // SIMPLIFICAR EL CODIGO REPETITIVO
        @Query("SELECT p FROM Product p JOIN FETCH p.brand JOIN FETCH p.category JOIN FETCH p.image WHERE p.status = true AND p.brand.status = true AND p.category.status = true")
        List<Product> findActiveProducts();

        // Métodos que reutilizan la consulta base
        default List<Product> findAllProducts() {
                return findActiveProducts();
        }

        default List<Product> findAllProductsByName(String name) {
                List<Product> products = findActiveProducts();
                return products.stream()
                                .filter(p -> p.getName().toLowerCase().contains(name.toLowerCase()))
                                .collect(Collectors.toList());
        }

        default List<Product> findAllProductsByOffer() {
                List<Product> products = findActiveProducts();
                return products.stream()
                                .filter(Product::getInOffer)
                                .collect(Collectors.toList());
        }

        default List<Product> findAllProductsByCategoryId(Long id) {
                List<Product> products = findActiveProducts();
                return products.stream()
                                .filter(p -> p.getCategory().getId().equals(id))
                                .collect(Collectors.toList());
        }

        // TODO: AÑADIR QUERY PARA FILTRAR PRODUCTOS POR NOMBRE, MARCA, CATEGORIA Y
        // OFERTA, SI UNO DE ELLOS ES NULL, NO SE DEBE CONSIDERAR DENTRO DE LA CONSULTA

        // default List<Product>
        // findAllProductsByNameAndCategoryIdAndBrandsIdsAndOffer(String name, Long
        // idCategory,
        // List<Number> idsBrands, Boolean Offer) {
        // List<Product> products = findActiveProducts();
        // return products.stream()
        // .filter(p -> p.getCategory().getId().equals(idCategory))
        // .filter(p -> p.getBrand().getId().equals(idsBrands))
        // .filter(p -> p.getName().toLowerCase().contains(name.toLowerCase()))
        // .collect(Collectors.toList());
        // }

        @Query("SELECT p FROM Product p JOIN FETCH p.brand b JOIN FETCH p.category c JOIN FETCH p.image " +
                        "WHERE p.status = true AND p.brand.status = true AND p.category.status = true " +
                        "AND (:name IS NULL OR LOWER(p.name) LIKE LOWER(CONCAT('%', :name, '%'))) " +
                        "AND (:idCategory IS NULL OR c.id = :idCategory) " +
                        "AND (:idsBrands IS NULL OR b.id IN :idsBrands) " +
                        "AND (:offer IS NULL OR p.inOffer = :offer)")
        List<Product> findAllFilteredProducts(
                        @Param("name") String name,
                        @Param("idCategory") Long idCategory,
                        @Param("idsBrands") List<Long> idsBrands,
                        @Param("offer") Boolean offer);

        // TODO: MODIFICAR ESTO PARA UNA SUPERCONSULTA
        @Query("SELECT p FROM Product p " +
                        "JOIN p.productFeature pf " +
                        "WHERE pf.featureValue.id IN :featureValues " +
                        "GROUP BY p.id")
        List<Product> findByFeatureValues(
                        @Param("featureValues") List<Long> featureValues);

        // SUPERCONSULTA A LA BASE DE DATOS PARA OBTENER UNA LISTA DE PRODUCTOS CON
        // TODOS LOS FILTROS DISPONIBLES

        // ADEMAS DEBE CONTAR CON UN PARAMETRO PARA ORDERNAR LOS PRODUCTOS POR FECHA DE
        // CREACIÓN, NOMBRE, MARCA Y PRECIO, TANTO DE FORMA ASCENDENTE COMO DESCENDENTE

        // @Query("SELECT p FROM Product p JOIN FETCH p.brand b JOIN FETCH p.category c
        // JOIN FETCH p.image JOIN FETCH p.productFeature pf "
        // +
        // "WHERE p.status = true AND p.brand.status = true AND p.category.status = true
        // " +
        // "AND (:name IS NULL OR LOWER(p.name) LIKE LOWER(CONCAT('%', :name, '%'))) " +
        // "AND (:idCategory IS NULL OR c.id = :idCategory) " +
        // "AND (:idsBrands IS NULL OR b.id IN :idsBrands) " +
        // "AND (:offer IS NULL OR p.inOffer = :offer)" +
        // "AND ((:minPrice IS NULL AND :maxPrice IS NULL) OR " +
        // " (p.offerPrice IS NOT NULL AND (:minPrice is NULL OR p.offerPrice >=
        // :minPrice)) AND " +
        // " (:maxPrice IS NULL OR p.offerPrice <= :maxPrice) OR " +
        // " (p.offerPrice IS NULL AND (:minPrice is NULL OR p.price >= :minPrice)) AND
        // " +
        // " (:maxPrice IS NULL OR p.price <= :maxPrice)) " +
        // "AND pf.featureValue.id IN :featureValues GROUP BY p.id")

        @Query("SELECT DISTINCT p FROM Product p JOIN p.brand b JOIN p.category c JOIN p.image JOIN p.productFeature pf "
                        +
                        "WHERE p.status = true AND p.brand.status = true AND p.category.status = true " +
                        "AND (:name IS NULL OR LOWER(p.name) LIKE LOWER(CONCAT('%', :name, '%'))) " +
                        "AND (:idCategory IS NULL OR c.id = :idCategory) " +
                        "AND (:idsBrands IS NULL OR b.id IN :idsBrands) " +
                        "AND (:offer IS NULL OR p.inOffer = :offer)" +
                        "AND ((:minPrice IS NULL AND :maxPrice IS NULL) OR " +
                        " (p.offerPrice IS NOT NULL AND (:minPrice is NULL OR p.offerPrice >= :minPrice)) AND " +
                        " (:maxPrice IS NULL OR p.offerPrice <= :maxPrice) OR " +
                        " (p.offerPrice IS NULL AND (:minPrice is NULL OR p.price >= :minPrice)) AND " +
                        " (:maxPrice IS NULL OR p.price <= :maxPrice)) " +
                        "AND (:featureValues IS NULL OR pf.featureValue.id IN :featureValues) " +
                        "ORDER BY " +
                        "CASE WHEN :sortBy = 'position' AND :order = 'asc' THEN p.createdAt END ASC, " +
                        "CASE WHEN :sortBy = 'position' AND :order IS NULL OR :order = 'desc' THEN p.createdAt END DESC, "
                        +
                        "CASE WHEN :sortBy = 'name' AND :order = 'asc' THEN p.name END ASC, " +
                        "CASE WHEN :sortBy = 'name' AND :order IS NULL OR :order = 'desc' THEN p.name END DESC, "
                        +
                        "CASE WHEN :sortBy = 'price' AND :order = 'asc' THEN (p.offerPrice IS NOT NULL OR p.price) END ASC, "
                        +
                        "CASE WHEN :sortBy = 'price' AND :order IS NULL OR :order = 'desc' THEN (p.offerPrice IS NOT NULL OR p.price) END ASC, "
                        +
                        "CASE WHEN :sortBy = 'brand' AND :order = 'asc' THEN p.brand.name END ASC, " +
                        "CASE WHEN :sortBy = 'brand' AND :order IS NULL OR :order = 'desc' THEN p.brand.name END DESC ")
        List<Product> findAllByFilters(
                        @Param("name") String name,
                        @Param("idCategory") Long idCategory,
                        @Param("idsBrands") List<Long> idsBrands,
                        @Param("offer") Boolean offer,
                        @Param("minPrice") Double minPrice,
                        @Param("maxPrice") Double maxPrice,
                        @Param("featureValues") List<Long> featureValues,
                        @Param("sortBy") String sort,
                        @Param("order") String order);

}

// EL USO DE FETCH EN UN QUERY
// https://medium.com/javarevisited/spring-jpa-when-to-use-join-fetch-a6cec898c4c6

// CONSULTA BASE
// https://docs.spring.io/spring-data/jpa/reference/repositories/query-methods-details.html

//
// TODO: UNA VEZ TERMINADO, IMPLEMENTAR PAGINACION
