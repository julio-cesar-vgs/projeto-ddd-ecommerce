package br.edu.ddd.project.productcatalog.domain.model.valueobjects;

import java.util.Objects;
import java.util.UUID;

public record ProductId(UUID value) {
    public ProductId {
        Objects.requireNonNull(value, "Product ID cannot be null");
    }

    public static ProductId generate() {
        return new ProductId(UUID.randomUUID());
    }
}