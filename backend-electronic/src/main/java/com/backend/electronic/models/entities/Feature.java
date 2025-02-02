package com.backend.electronic.models.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

// TODO: MODIFICAR ESTO
@Builder
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "feature")
public class Feature {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name; // Característica (e.g., "Color", "Peso")

    // @OneToOne
    // @JoinColumn(name = "value_id")
    // private Value value;

}

// Una caracteristica tiene varios valores asignados
// @OneToMany(cascade = CascadeType.ALL, mappedBy = "feature")
// private List<Value> values;
