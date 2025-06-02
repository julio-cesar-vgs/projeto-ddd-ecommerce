package br.edu.ddd.project.ordermanagement.domain.model.valueobject;

import java.util.Objects;
import java.util.UUID;

public record OrderId(UUID value) {
    public OrderId {
        Objects.requireNonNull(value, "Order ID cannot be null");
    }

    public static OrderId generate() {
        return new OrderId(UUID.randomUUID());
    }
}


