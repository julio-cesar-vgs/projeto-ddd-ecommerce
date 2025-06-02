package br.edu.ddd.project.productcatalog.domain.model.valueobjects;

public record Stock(int quantity) {
    public Stock {
        if (quantity < 0) {
            throw new IllegalArgumentException("Stock quantity cannot be negative");
        }
    }

    public static Stock empty() {
        return new Stock(0);
    }

    public boolean isAvailable() {
        return quantity > 0;
    }

    public Stock reduce(int amount) {
        if (amount > quantity) {
            throw new IllegalArgumentException("Cannot reduce more than available stock");
        }
        return new Stock(quantity - amount);
    }

    public Stock increase(int amount) {
        return new Stock(quantity + amount);
    }
}