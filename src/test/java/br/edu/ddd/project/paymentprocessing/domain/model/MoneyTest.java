package br.edu.ddd.project.paymentprocessing.domain.model;

import br.edu.ddd.project.paymentprocessing.domain.model.valueobjects.Money;
import org.junit.jupiter.api.Test;

import java.util.Currency;

import static org.junit.jupiter.api.Assertions.*;

class MoneyTest {
    @Test
    void zeroFactoryAllowsZeroAmount() {
        Money zero = Money.zero(Currency.getInstance("USD"));
        assertEquals(0, zero.amount().intValue());
    }
}
