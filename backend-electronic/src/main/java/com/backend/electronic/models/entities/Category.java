package com.backend.electronic.models.entities;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
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
@Table(name = "category")
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "El nombre no puede estar vacío")
    @Column(unique = true)
    private String name;

    @Column(columnDefinition = "TINYINT(1)", nullable = false)
    private Boolean status;

    @OneToMany(mappedBy = "category", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CategoryFeature> categoryFeatures = new ArrayList<>();

}

// @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
// @JoinTable(name = "category_feature", joinColumns = @JoinColumn(name =
// "category_id", referencedColumnName = "id"), inverseJoinColumns =
// @JoinColumn(name = "feature_id", referencedColumnName = "id"))
// private List<Feature> feature;

// Relacion hacia productos
// @OneToMany(mappedBy = "category", cascade = CascadeType.ALL, orphanRemoval =
// true, fetch = FetchType.LAZY)
// private List<Product> product;

// Relacion hacia caracteristicas (un producto corresponde a una categoria y esa
// categoria tiene ciertas caracteristicas)

// Una caracteristica tiene varios valores asignados
// @ManyToMany(cascade = CascadeType.ALL, mappedBy = "category")
