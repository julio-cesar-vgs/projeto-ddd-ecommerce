package br.edu.ddd.project.paymentprocessing.domain.model.valueobjects;

import java.util.Objects;

public record PaymentDetails(
        String cardNumber,
        String cardHolderName,
        String expiryDate,
        String cvv
) {
    public PaymentDetails {
        Objects.requireNonNull(cardNumber, "Card number cannot be null");
        Objects.requireNonNull(cardHolderName, "Card holder name cannot be null");
    }

    public String getMaskedCardNumber() {
        if (cardNumber.length() < 4) return "****";
        return "**** **** **** " + cardNumber.substring(cardNumber.length() - 4);
    }
}
