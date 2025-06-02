package br.edu.ddd.project.paymentprocessing.domain.model.valueobjects;

import java.util.Objects;
import java.util.UUID;

public record OrderId(UUID value) {
    public OrderId {
        Objects.requireNonNull(value, "Order ID cannot be null");
    }
}