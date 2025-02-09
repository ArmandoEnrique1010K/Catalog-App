package com.backend.electronic.models.requests;

import com.backend.electronic.models.entities.Brand;
import com.backend.electronic.models.entities.Category;
import com.backend.electronic.models.entities.Image;

import jakarta.annotation.Nullable;
import jakarta.persistence.Column;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

// La diferencia con Product es que este Request no tiene la anotación unique en el codigo del producto

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductRequest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "El nombre no puede estar vacío")
    @Column(nullable = false)
    private String name;

    // TODO: EL CODIGO DE PRODUCTO SE DEBE GENERAR DE UNA FORMA ALEATORIA???
    // Codigo del producto (se genera de una forma)
    @NotBlank(message = "El código no puede estar vacío")
    @Column(nullable = false)
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
    @Nullable
    @Column
    private Double offerPrice;

    // Descripción del producto
    @NotBlank(message = "La descripción no puede estar vacía")
    @Column(columnDefinition = "TEXT")
    private String description;

    // Relacion con las otras entidades

    // Relacion hacia categoria
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

}
