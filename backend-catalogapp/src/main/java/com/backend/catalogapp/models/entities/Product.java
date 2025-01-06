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
    private Long id_product;

    // Codigo del producto (se genera de una forma)
    @Column
    private String code;

    @Column
    private String name;

    // Campo para verificar que si un producto esta en oferta
    @Column
    private Boolean isOffer;

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

    // Fecha de ultima edición
    @Column
    private LocalDateTime lastEditionDate;

    // Fecha de creación
    @Column
    private LocalDateTime createDate;

    // Relacion con las otras entidades

    // Relacion hacia categoria
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_category")
    private Category category;

    // Relacion hacia marca
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_brand")
    private Brand brand;

    // Relacion hacia imagen
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_image")
    private Image image;

}
