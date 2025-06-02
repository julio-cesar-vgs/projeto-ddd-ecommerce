package br.edu.ddd.project.ordermanagement.domain.model.valueobject;

import java.util.Objects;
import java.util.UUID;

public record ProductId(UUID value) {
    public ProductId {
        Objects.requireNonNull(value, "Product ID cannot be null");
    }
}
