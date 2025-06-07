package br.edu.ddd.project.productcatalog.domain.model.valueobjects;

import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class ProductIdTest {

    @Test
    void constructorShouldRequireNonNullValue() {
        assertThrows(NullPointerException.class, () -> new ProductId(null));
    }

    @Test
    void generateShouldProduceUniqueIds() {
        ProductId id1 = ProductId.generate();
        ProductId id2 = ProductId.generate();
        assertNotNull(id1.value());
        assertNotNull(id2.value());
        assertNotEquals(id1, id2);
    }

    @Test
    void valueShouldReturnProvidedUuid() {
        UUID uuid = UUID.randomUUID();
        ProductId id = new ProductId(uuid);
        assertEquals(uuid, id.value());
    }
}
