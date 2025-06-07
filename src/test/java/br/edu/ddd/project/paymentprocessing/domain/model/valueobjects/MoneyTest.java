package br.edu.ddd.project.paymentprocessing.domain.model.valueobjects;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Currency;

import static org.junit.jupiter.api.Assertions.*;

class MoneyTest {
    @Test
    void zeroShouldReturnValidInstance() {
        Currency currency = Currency.getInstance("USD");
        Money zero = assertDoesNotThrow(() -> Money.zero(currency));
        assertEquals(0, zero.amount().compareTo(BigDecimal.ZERO));
        assertEquals(currency, zero.currency());
    }
}
