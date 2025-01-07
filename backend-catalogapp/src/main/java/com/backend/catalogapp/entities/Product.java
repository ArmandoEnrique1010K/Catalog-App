package com.backend.catalogapp.entities;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "product")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Codigo del producto (se genera de una forma)
    @Column
    private String code;

    @Column
    private String name;

    // Campo para verificar que si un producto esta en oferta
    @Column
    private Boolean inOffer;

    // Precio original y precio de oferta
    @Column
    private Double price;

    @Column
    private Double offerPrice;

    // Estado (requerido para eliminar productos, de tal manera que solo cambie el
    // estado)
    // Esta es la mejor definición de la columna para un tipo Boolean
    // "En el caso de subir un archivo .csv a la base de datos"
    @Column(columnDefinition = "TINYINT(1)", nullable = false)
    private Boolean status;

    // Descripción del producto
    @Column
    private String description;

    // Fecha de ultima edición
    @Column
    private LocalDateTime updatedAt;

    // Fecha de creación
    @Column
    private LocalDateTime createdAt;

    // Relacion con las otras entidades

    // Relacion hacia categoria
    // TODO: Investigar FetchType.LAZY
    // @ManyToOne(fetch = FetchType.LAZY)

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    // Relacion hacia marca
    @ManyToOne
    @JoinColumn(name = "brand_id")
    private Brand brand;

    // Relacion hacia imagen
    @OneToOne
    @JoinColumn(name = "image_id")
    private Image image;

    // Relacion hacia detalles del producto
    // @OneToOne
    // @JoinColumn(name = "productDetails_id")
    // private ProductDetails productDetails;

}
