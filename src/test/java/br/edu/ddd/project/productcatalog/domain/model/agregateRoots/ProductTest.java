package br.edu.ddd.project.productcatalog.domain.model.agregateRoots;

import br.edu.ddd.project.productcatalog.domain.model.valueobjects.*;
import br.edu.ddd.project.ordermanagement.domain.model.valueobject.Money;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Currency;

import static org.junit.jupiter.api.Assertions.*;

class ProductTest {
    @Test
    void stockUpdatesAvailability() {
        Product product = Product.create(
                new ProductDescription("Test", "short", "full"),
                Money.of(BigDecimal.ONE, Currency.getInstance("USD")),
                CategoryId.generate(),
                new Stock(1)
        );
        assertTrue(product.isAvailable());
        product.reduceStock(1);
        assertFalse(product.isAvailable());
    }
}
