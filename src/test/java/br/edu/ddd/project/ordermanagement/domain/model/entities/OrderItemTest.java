package br.edu.ddd.project.ordermanagement.domain.model.entities;

import br.edu.ddd.project.ordermanagement.domain.model.valueobject.Money;
import br.edu.ddd.project.ordermanagement.domain.model.valueobject.ProductId;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Currency;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class OrderItemTest {
    @Test
    void createCalculatesTotalPrice() {
        Money unitPrice = Money.of(BigDecimal.TEN, Currency.getInstance("USD"));
        OrderItem item = OrderItem.create(new ProductId(UUID.randomUUID()), "Item", 2, unitPrice);
        assertEquals(unitPrice.multiply(2), item.getTotalPrice());
    }
}
