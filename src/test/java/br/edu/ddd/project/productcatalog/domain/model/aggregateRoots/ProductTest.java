package br.edu.ddd.project.productcatalog.domain.model.aggregateRoots;

import br.edu.ddd.project.ordermanagement.domain.model.valueobject.Money;
import br.edu.ddd.project.productcatalog.domain.model.enums.ProductStatus;
import br.edu.ddd.project.productcatalog.domain.model.valueobjects.CategoryId;
import br.edu.ddd.project.productcatalog.domain.model.valueobjects.ProductDescription;
import br.edu.ddd.project.productcatalog.domain.model.valueobjects.Stock;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Currency;

import static org.junit.jupiter.api.Assertions.*;

class ProductTest {

    private Product createSampleProduct() {
        ProductDescription description = new ProductDescription("Test", "short", "full");
        Money price = Money.of(BigDecimal.TEN, Currency.getInstance("USD"));
        CategoryId categoryId = CategoryId.generate();
        return Product.create(description, price, categoryId, new Stock(5));
    }

    @Test
    void createShouldInitializeProduct() {
        ProductDescription description = new ProductDescription("Prod", "short", "full");
        Money price = Money.of(new BigDecimal("20.00"), Currency.getInstance("USD"));
        CategoryId categoryId = CategoryId.generate();
        Stock stock = new Stock(3);

        Product product = Product.create(description, price, categoryId, stock);

        assertNotNull(product.getId());
        assertEquals(description, product.getDescription());
        assertEquals(price, product.getPrice());
        assertEquals(categoryId, product.getCategoryId());
        assertEquals(stock, product.getStock());
        assertEquals(ProductStatus.ACTIVE, product.getStatus());
        assertTrue(product.getAttributes().isEmpty());
        assertNotNull(product.getCreatedAt());
        assertNotNull(product.getUpdatedAt());
    }

    @Test
    void updatePriceShouldChangePriceAndTimestamp() {
        Product product = createSampleProduct();
        LocalDateTime previousUpdate = product.getUpdatedAt();
        Money newPrice = Money.of(new BigDecimal("30.00"), Currency.getInstance("USD"));

        product.updatePrice(newPrice);

        assertEquals(newPrice, product.getPrice());
        assertTrue(product.getUpdatedAt().isAfter(previousUpdate));
    }

    @Test
    void updateStockShouldChangeStatusBasedOnAvailability() {
        Product product = createSampleProduct();

        product.updateStock(new Stock(0));
        assertEquals(ProductStatus.OUT_OF_STOCK, product.getStatus());
        assertEquals(0, product.getStock().quantity());

        product.updateStock(new Stock(2));
        assertEquals(ProductStatus.ACTIVE, product.getStatus());
        assertEquals(2, product.getStock().quantity());
    }

    @Test
    void addAndReduceStockShouldUpdateQuantityAndStatus() {
        Product product = createSampleProduct();

        product.reduceStock(5);
        assertEquals(0, product.getStock().quantity());
        assertEquals(ProductStatus.OUT_OF_STOCK, product.getStatus());

        product.addStock(3);
        assertEquals(3, product.getStock().quantity());
        assertEquals(ProductStatus.ACTIVE, product.getStatus());
    }

    @Test
    void addAttributeShouldStoreKeyValue() {
        Product product = createSampleProduct();
        product.addAttribute("color", "blue");

        assertEquals("blue", product.getAttributes().get("color"));
    }
}
