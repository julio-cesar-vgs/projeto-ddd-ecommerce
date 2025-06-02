package br.edu.ddd.project.paymentprocessing.domain.model.valueobjects;

import java.util.Objects;
import java.util.UUID;

public record PaymentId(UUID value) {
    public PaymentId {
        Objects.requireNonNull(value, "Payment ID cannot be null");
    }

    public static PaymentId generate() {
        return new PaymentId(UUID.randomUUID());
    }
}