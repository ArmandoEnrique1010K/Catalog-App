package com.backend.electronic.models.entities;

import org.springframework.web.multipart.MultipartFile;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "productImage")

public class ProductImage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // TODO: ¿BORRAR ESTAS VALIDACIONES, PUES EL NOMBRE DE LA IMAGEN SE GENERA
    // AUTOMATICAMENTE EN EL SERVICIO?
    @NotBlank(message = "El nombre no puede estar vacío")
    @Column(nullable = false)
    private String name;

    @Transient
    private MultipartFile file;

}

// Relación de uno a uno con producto
// @OneToOne(mappedBy = "image", cascade = CascadeType.ALL, orphanRemoval =
// true, fetch = FetchType.LAZY)
// private Product product;
