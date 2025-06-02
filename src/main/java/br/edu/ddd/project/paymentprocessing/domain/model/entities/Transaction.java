package br.edu.ddd.project.paymentprocessing.domain.model.entities;

import br.edu.ddd.project.paymentprocessing.domain.model.enums.TransactionType;
import br.edu.ddd.project.paymentprocessing.domain.model.valueobjects.Money;
import br.edu.ddd.project.paymentprocessing.domain.model.valueobjects.TransactionId;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Transaction {
    private TransactionId id;
    private TransactionType type;
    private Money amount;
    private String externalTransactionId;
    private String description;
    private LocalDateTime processedAt;
    private Map<String, String> metadata;

    public static Transaction createPayment(Money amount, String description) {
        return Transaction.builder()
                .id(TransactionId.generate())
                .type(TransactionType.PAYMENT)
                .amount(amount)
                .description(description)
                .metadata(new HashMap<>())
                .build();
    }

    public static Transaction createRefund(Money amount, String description) {
        return Transaction.builder()
                .id(TransactionId.generate())
                .type(TransactionType.REFUND)
                .amount(amount)
                .description(description)
                .metadata(new HashMap<>())
                .build();
    }

    public void markAsProcessed(String externalTransactionId) {
        this.externalTransactionId = externalTransactionId;
        this.processedAt = LocalDateTime.now();
    }

    public boolean isProcessed() {
        return processedAt != null;
    }
}
