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
import jakarta.persistence.UniqueConstraint;
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
@Table(name = "feature", uniqueConstraints = @UniqueConstraint(columnNames = { "name" }))
// Entidad para las caracteristicas de un producto (dentro de ficha tecnica)
public class Feature {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "El nombre no puede estar vacío")
    // NO DEBERIA SER UNICO
    @Column(nullable = false) // 🔹 Asegura que el nombre sea único
    private String name; // Característica (e.g., "Color", "Peso")

    @Column(columnDefinition = "TINYINT(1)", nullable = false)
    private Boolean status = true;

    @OneToMany(mappedBy = "feature", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<FeatureValue> featureValues = new ArrayList<>();

}
// @OneToOne
// @JoinColumn(name = "value_id")
// private Value value;

// Una caracteristica tiene varios valores asignados
// @OneToMany(cascade = CascadeType.ALL, mappedBy = "feature")
// private List<Value> values;
