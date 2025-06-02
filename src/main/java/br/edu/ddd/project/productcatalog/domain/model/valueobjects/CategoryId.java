package br.edu.ddd.project.productcatalog.domain.model.valueobjects;

import java.util.Objects;
import java.util.UUID;

public record CategoryId(UUID value) {
    public CategoryId {
        Objects.requireNonNull(value, "Category ID cannot be null");
    }

    public static CategoryId generate() {
        return new CategoryId(UUID.randomUUID());
    }
}