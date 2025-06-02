package br.edu.ddd.project.ordermanagement.domain.model.agregateRoots;

import br.edu.ddd.project.ordermanagement.domain.model.entities.OrderItem;
import br.edu.ddd.project.ordermanagement.domain.model.enums.OrderStatus;
import br.edu.ddd.project.ordermanagement.domain.model.valueobject.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Currency;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Order {
    private OrderId id;
    private CustomerId customerId;
    private List<OrderItem> items;
    private Money totalAmount;
    private OrderStatus status;
    private ShippingAddress shippingAddress;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public static Order create(CustomerId customerId, ShippingAddress shippingAddress) {
        return Order.builder()
                .id(OrderId.generate())
                .customerId(customerId)
                .items(new ArrayList<>())
                .totalAmount(Money.zero(Currency.getInstance("USD")))
                .status(OrderStatus.PENDING)
                .shippingAddress(shippingAddress)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();
    }

    public void addItem(ProductId productId, String productName, int quantity, Money unitPrice) {
        OrderItem item = OrderItem.create(productId, productName, quantity, unitPrice);
        items.add(item);
        recalculateTotal();
        this.updatedAt = LocalDateTime.now();
    }

    public void removeItem(ProductId productId) {
        items.removeIf(item -> item.getProductId().equals(productId));
        recalculateTotal();
        this.updatedAt = LocalDateTime.now();
    }

    public void confirm() {
        if (items.isEmpty()) {
            throw new IllegalStateException("Cannot confirm order without items");
        }
        this.status = OrderStatus.CONFIRMED;
        this.updatedAt = LocalDateTime.now();
    }

    public void cancel() {
        if (status == OrderStatus.SHIPPED || status == OrderStatus.DELIVERED) {
            throw new IllegalStateException("Cannot cancel shipped or delivered order");
        }
        this.status = OrderStatus.CANCELLED;
        this.updatedAt = LocalDateTime.now();
    }

    private void recalculateTotal() {
        this.totalAmount = items.stream()
                .map(OrderItem::getTotalPrice)
                .reduce(Money.zero(Currency.getInstance("USD")), Money::add);
    }

    public boolean canBeModified() {
        return status == OrderStatus.PENDING;
    }
}
