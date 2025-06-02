package br.edu.ddd.project.ordermanagement.domain.model.valueobject;

import java.util.Objects;

public record ShippingAddress(
        String street,
        String city,
        String state,
        String zipCode,
        String country
) {
    public ShippingAddress {
        Objects.requireNonNull(street, "Street cannot be null");
        Objects.requireNonNull(city, "City cannot be null");
        Objects.requireNonNull(zipCode, "Zip code cannot be null");
    }
}