package com.backend.catalogapp.entities;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
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
@Table(name = "category")
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // @Column(unique = true, nullable = false)
    private String name;

    // @Column(nullable = false)
    private Boolean status;

    // Relacion hacia productos
    // @OneToMany(mappedBy = "category", cascade = CascadeType.ALL, orphanRemoval =
    // true, fetch = FetchType.LAZY)
    // private List<Product> product;

    // Relacion hacia caracteristicas (un producto corresponde a una categoria y esa
    // categoria tiene ciertas caracteristicas)

    // Una caracteristica tiene varios valores asignados
    // @ManyToMany(cascade = CascadeType.ALL, mappedBy = "category")

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(name = "category_feature", joinColumns = @JoinColumn(name = "category_id", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "feature_id", referencedColumnName = "id"))
    private List<Feature> feature;

}
