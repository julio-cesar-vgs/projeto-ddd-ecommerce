package br.edu.ddd.project.paymentprocessing.domain.model;

import br.edu.ddd.project.paymentprocessing.domain.model.entities.Transaction;
import br.edu.ddd.project.paymentprocessing.domain.model.enums.PaymentMethod;
import br.edu.ddd.project.paymentprocessing.domain.model.enums.PaymentStatus;
import br.edu.ddd.project.paymentprocessing.domain.model.valueobjects.*;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Currency;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class PaymentTest {
    @Test
    void lifecycleProcessingToComplete() {
        Money amount = Money.of(BigDecimal.TEN, Currency.getInstance("USD"));
        Payment payment = Payment.create(
                new OrderId(UUID.randomUUID()),
                amount,
                PaymentMethod.CREDIT_CARD,
                new PaymentDetails("1234123412341234", "Tester", "12/30", "123")
        );
        payment.startProcessing();
        assertEquals(PaymentStatus.PROCESSING, payment.getStatus());
        payment.complete("ext-id");
        assertEquals(PaymentStatus.COMPLETED, payment.getStatus());
        assertTrue(payment.isSuccessful());
    }
}
