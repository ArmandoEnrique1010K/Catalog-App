package com.backend.electronic.models.entities;

import java.util.List;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

// TODO: MODIFICAR ESTO
@Entity
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "feature_value", uniqueConstraints = @UniqueConstraint(columnNames = { "feature_id", "value" }))
public class FeatureValue {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false) // 🔹 No permitir que feature sea null
    // @Cascade(CascadeType.PERSIST) // TODO: INVESTIGAR @CASCADE

    // TODO: SI DESACTIVO nullable = false, vuelve a ocurrir el error de que se sube
    // 2 veces el registro en la base de datos en la tablas product_feature e
    // feature_value, pero se sube el registro, el primero sin feature_id y el
    // segundo se asigna feature_id
    @JoinColumn(name = "feature_id", nullable = false /* , nullable = false */) // 🔹 Asegurar integridad en la BD
    private Feature feature;

    @NotBlank
    @Column(nullable = false)
    private String value;

    @OneToMany(mappedBy = "featureValue", cascade = jakarta.persistence.CascadeType.ALL, orphanRemoval = true)
    private List<ProductFeature> productFeatures;

}
