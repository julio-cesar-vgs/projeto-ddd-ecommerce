package br.edu.ddd.project.productcatalog.domain.model.valueobjects;

import java.util.Objects;

public record ProductDescription(
        String name,
        String shortDescription,
        String fullDescription
) {
    public ProductDescription {
        Objects.requireNonNull(name, "Product name cannot be null");
        if (name.trim().isEmpty()) {
            throw new IllegalArgumentException("Product name cannot be empty");
        }
    }
}