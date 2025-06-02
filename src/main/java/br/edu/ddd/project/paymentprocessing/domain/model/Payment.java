package br.edu.ddd.project.paymentprocessing.domain.model;

import br.edu.ddd.project.paymentprocessing.domain.model.entities.Transaction;
import br.edu.ddd.project.paymentprocessing.domain.model.enums.PaymentMethod;
import br.edu.ddd.project.paymentprocessing.domain.model.enums.PaymentStatus;
import br.edu.ddd.project.paymentprocessing.domain.model.enums.TransactionType;
import br.edu.ddd.project.paymentprocessing.domain.model.valueobjects.Money;
import br.edu.ddd.project.paymentprocessing.domain.model.valueobjects.OrderId;
import br.edu.ddd.project.paymentprocessing.domain.model.valueobjects.PaymentDetails;
import br.edu.ddd.project.paymentprocessing.domain.model.valueobjects.PaymentId;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

// domain/model/Payment.java
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Payment {
    private PaymentId id;
    private OrderId orderId;
    private Money amount;
    private PaymentMethod method;
    private PaymentStatus status;
    private PaymentDetails paymentDetails;
    private List<Transaction> transactions;
    private String failureReason;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public static Payment create(OrderId orderId, Money amount, PaymentMethod method, PaymentDetails paymentDetails) {
        return Payment.builder()
                .id(PaymentId.generate())
                .orderId(orderId)
                .amount(amount)
                .method(method)
                .status(PaymentStatus.PENDING)
                .paymentDetails(paymentDetails)
                .transactions(new ArrayList<>())
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();
    }

    public void startProcessing() {
        if (status != PaymentStatus.PENDING) {
            throw new IllegalStateException("Can only start processing pending payments");
        }
        this.status = PaymentStatus.PROCESSING;
        this.updatedAt = LocalDateTime.now();
    }

    public void complete(String externalTransactionId) {
        if (status != PaymentStatus.PROCESSING) {
            throw new IllegalStateException("Can only complete processing payments");
        }

        Transaction transaction = Transaction.createPayment(amount, "Payment for order " + orderId.value());
        transaction.markAsProcessed(externalTransactionId);
        transactions.add(transaction);

        this.status = PaymentStatus.COMPLETED;
        this.updatedAt = LocalDateTime.now();
    }

    public void fail(String reason) {
        if (status != PaymentStatus.PROCESSING) {
            throw new IllegalStateException("Can only fail processing payments");
        }

        this.status = PaymentStatus.FAILED;
        this.failureReason = reason;
        this.updatedAt = LocalDateTime.now();
    }

    public void cancel() {
        if (status == PaymentStatus.COMPLETED || status == PaymentStatus.REFUNDED) {
            throw new IllegalStateException("Cannot cancel completed or refunded payments");
        }

        this.status = PaymentStatus.CANCELLED;
        this.updatedAt = LocalDateTime.now();
    }

    public Payment refund(Money refundAmount, String reason) {
        if (status != PaymentStatus.COMPLETED) {
            throw new IllegalStateException("Can only refund completed payments");
        }

        if (refundAmount.amount().compareTo(amount.amount()) > 0) {
            throw new IllegalArgumentException("Refund amount cannot exceed payment amount");
        }

        Transaction refundTransaction = Transaction.createRefund(refundAmount, reason);
        transactions.add(refundTransaction);

        this.status = PaymentStatus.REFUNDED;
        this.updatedAt = LocalDateTime.now();

        return this;
    }

    public boolean isSuccessful() {
        return status == PaymentStatus.COMPLETED;
    }

    public Money getTotalRefunded() {
        return transactions.stream()
                .filter(t -> t.getType() == TransactionType.REFUND)
                .map(Transaction::getAmount)
                .reduce(Money.zero(amount.currency()), Money::add);
    }
}
