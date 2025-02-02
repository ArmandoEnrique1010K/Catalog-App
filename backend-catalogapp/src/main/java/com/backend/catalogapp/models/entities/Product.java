package com.backend.catalogapp.models.entities;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
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

    @NotBlank(message = "El nombre no puede estar vacío")
    @Column(nullable = false)
    private String name;

    // Codigo del producto (se genera de una forma)
    @NotBlank(message = "El código no puede estar vacío")
    @Column(unique = true, nullable = false)
    private String code;

    // Campo para verificar que si un producto esta en oferta
    @Column
    private Boolean inOffer;

    // Precio original y precio de oferta
    @NotNull(message = "El precio es obligatorio")
    @Positive(message = "El precio debe ser un valor positivo")
    @Column(nullable = false)
    private Double price;

    @Positive(message = "El precio de oferta debe ser positivo")
    @NotNull(message = "El precio es obligatorio")
    @Column
    private Double offerPrice;

    // Estado (requerido para eliminar productos, de tal manera que solo cambie el
    // estado)
    // Esta es la mejor definición de la columna para un tipo Boolean
    // "En el caso de subir un archivo .csv a la base de datos"
    @Column(columnDefinition = "TINYINT(1)", nullable = false)
    private Boolean status;

    // Descripción del producto
    @NotBlank(message = "La descripción no puede estar vacía")
    @Column(columnDefinition = "TEXT")
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

    @NotNull(message = "La categoría es obligatoria")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private Category category;

    // Relacion hacia marca
    @NotNull(message = "La marca es obligatoria")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "brand_id")
    private Brand brand;

    // Relacion hacia imagen
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "image_id", referencedColumnName = "id")
    private Image image;

    // Relacion hacia detalles del producto
    // @OneToOne
    // @JoinColumn(name = "productDetails_id")
    // private ProductDetails productDetails;

}
