package com.backend.electronic.models.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
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
@Table(name = "brand")
public class Brand {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "El nombre no puede estar vacío")
    @Column(unique = true)
    private String name;

    @Column(columnDefinition = "TINYINT(1)", nullable = false)
    private Boolean status;
}

// Relacion hacia productos
// @OneToMany(mappedBy = "brand", cascade = CascadeType.ALL, orphanRemoval =
// true, fetch = FetchType.LAZY)
// private List<Product> product;

// https://www-geeksforgeeks-org.translate.goog/spring-mvc-validation/?_x_tr_sl=en&_x_tr_tl=es&_x_tr_hl=es&_x_tr_pto=sge#:~:text=//%20@NotNull:%20The%20CharSequence%2C,length%20is%20greater%20than%20zero.
