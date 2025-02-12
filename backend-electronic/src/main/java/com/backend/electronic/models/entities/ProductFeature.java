package com.backend.electronic.models.entities;

import org.hibernate.annotations.Cascade;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
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
@Table(name = "product_feature", uniqueConstraints = @UniqueConstraint(columnNames = { "product_id",
        "feature_value_id" }))
public class ProductFeature {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne // Cascada para Feature
    // @Cascade(CascadeType.PERSIST) // TODO: INVESTIGAR @CASCADE
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @ManyToOne(fetch = FetchType.LAZY, targetEntity = Feature.class) // Cascada para Feature
    // @Cascade(CascadeType.PERSIST) // TODO: INVESTIGAR @CASCADE
    @JoinColumn(name = "feature_id", nullable = false)
    // @JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
    private Feature feature;

    @ManyToOne(fetch = FetchType.LAZY, targetEntity = FeatureValue.class)
    // @Cascade(CascadeType.PERSIST) // TODO: INVESTIGAR @CASCADE
    @JoinColumn(name = "feature_value_id", nullable = false)
    // @JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
    private FeatureValue featureValue;

    // Getters and Setters
}
