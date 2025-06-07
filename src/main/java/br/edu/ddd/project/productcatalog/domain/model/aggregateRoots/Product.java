package br.edu.ddd.project.productcatalog.domain.model.aggregateRoots;


import br.edu.ddd.project.ordermanagement.domain.model.valueobject.Money;
import br.edu.ddd.project.productcatalog.domain.model.enums.ProductStatus;
import br.edu.ddd.project.productcatalog.domain.model.valueobjects.CategoryId;
import br.edu.ddd.project.productcatalog.domain.model.valueobjects.ProductDescription;
import br.edu.ddd.project.productcatalog.domain.model.valueobjects.ProductId;
import br.edu.ddd.project.productcatalog.domain.model.valueobjects.Stock;
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
public class Product {
    private ProductId id;
    private ProductDescription description;
    private Money price;
    private CategoryId categoryId;
    private Stock stock;
    private ProductStatus status;
    private String imageUrl;
    private Map<String, String> attributes;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public static Product create(ProductDescription description, Money price, CategoryId categoryId, Stock initialStock) {
        return Product.builder()
                .id(ProductId.generate())
                .description(description)
                .price(price)
                .categoryId(categoryId)
                .stock(initialStock)
                .status(ProductStatus.ACTIVE)
                .attributes(new HashMap<>())
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();
    }

    public void updatePrice(Money newPrice) {
        this.price = newPrice;
        this.updatedAt = LocalDateTime.now();
    }

    public void updateStock(Stock newStock) {
        this.stock = newStock;
        updateStatus();
        this.updatedAt = LocalDateTime.now();
    }

    public void addStock(int quantity) {
        this.stock = stock.increase(quantity);
        updateStatus();
        this.updatedAt = LocalDateTime.now();
    }

    public void reduceStock(int quantity) {
        this.stock = stock.reduce(quantity);
        updateStatus();
        this.updatedAt = LocalDateTime.now();
    }

    public void discontinue() {
        this.status = ProductStatus.DISCONTINUED;
        this.updatedAt = LocalDateTime.now();
    }

    public boolean isAvailable() {
        return status == ProductStatus.ACTIVE && stock.isAvailable();
    }

    private void updateStatus() {
        if (status == ProductStatus.ACTIVE && !stock.isAvailable()) {
            this.status = ProductStatus.OUT_OF_STOCK;
        } else if (status == ProductStatus.OUT_OF_STOCK && stock.isAvailable()) {
            this.status = ProductStatus.ACTIVE;
        }
    }

    public void addAttribute(String key, String value) {
        this.attributes.put(key, value);
        this.updatedAt = LocalDateTime.now();
    }
}
