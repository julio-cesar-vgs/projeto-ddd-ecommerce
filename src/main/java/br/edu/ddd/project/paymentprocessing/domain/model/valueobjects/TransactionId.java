package br.edu.ddd.project.paymentprocessing.domain.model.valueobjects;

import java.util.Objects;
import java.util.UUID;

public record TransactionId(UUID value) {
    public TransactionId {
        Objects.requireNonNull(value, "Transaction ID cannot be null");
    }

    public static TransactionId generate() {
        return new TransactionId(UUID.randomUUID());
    }
}
