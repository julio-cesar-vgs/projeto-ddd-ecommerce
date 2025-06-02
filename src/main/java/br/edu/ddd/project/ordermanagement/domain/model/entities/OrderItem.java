package br.edu.ddd.project.ordermanagement.domain.model.entities;

import br.edu.ddd.project.ordermanagement.domain.model.valueobject.Money;
import br.edu.ddd.project.ordermanagement.domain.model.valueobject.ProductId;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderItem {
    private ProductId productId;
    private String productName;
    private int quantity;
    private Money unitPrice;
    private Money totalPrice;

    public static OrderItem create(ProductId productId, String productName, int quantity, Money unitPrice) {
        if (quantity <= 0) {
            throw new IllegalArgumentException("Quantity must be positive");
        }

        return OrderItem.builder()
                .productId(productId)
                .productName(productName)
                .quantity(quantity)
                .unitPrice(unitPrice)
                .totalPrice(unitPrice.multiply(quantity))
                .build();
    }

    public void updateQuantity(int newQuantity) {
        if (newQuantity <= 0) {
            throw new IllegalArgumentException("Quantity must be positive");
        }
        this.quantity = newQuantity;
        this.totalPrice = unitPrice.multiply(newQuantity);
    }
}
