package br.edu.ddd.project.productcatalog.domain.model.valueobjects;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class StockTest {

    @Test
    void constructorShouldRejectNegativeQuantity() {
        assertThrows(IllegalArgumentException.class, () -> new Stock(-1));
    }

    @Test
    void emptyShouldReturnZeroAndNotAvailable() {
        Stock stock = Stock.empty();
        assertEquals(0, stock.quantity());
        assertFalse(stock.isAvailable());
    }

    @Test
    void reduceShouldDecreaseQuantity() {
        Stock original = new Stock(5);
        Stock reduced = original.reduce(3);
        assertEquals(2, reduced.quantity());
        assertEquals(5, original.quantity());
    }

    @Test
    void reduceShouldNotAllowMoreThanAvailable() {
        Stock original = new Stock(2);
        assertThrows(IllegalArgumentException.class, () -> original.reduce(3));
    }

    @Test
    void increaseShouldReturnStockWithAddedQuantity() {
        Stock original = new Stock(2);
        Stock increased = original.increase(3);
        assertEquals(5, increased.quantity());
    }

    @Test
    void isAvailableShouldReflectQuantity() {
        assertFalse(new Stock(0).isAvailable());
        assertTrue(new Stock(1).isAvailable());
    }
}
