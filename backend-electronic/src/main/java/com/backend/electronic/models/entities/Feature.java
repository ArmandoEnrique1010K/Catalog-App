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
@Table(name = "feature")
// Entidad para las caracteristicas de un producto (dentro de ficha tecnica)
public class Feature {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "El nombre no puede estar vacío")
    @Column(unique = true)
    private String name; // Característica (e.g., "Color", "Peso")

    @Column(columnDefinition = "TINYINT(1)", nullable = false)
    private Boolean status;

}
// @OneToOne
// @JoinColumn(name = "value_id")
// private Value value;

// Una caracteristica tiene varios valores asignados
// @OneToMany(cascade = CascadeType.ALL, mappedBy = "feature")
// private List<Value> values;
