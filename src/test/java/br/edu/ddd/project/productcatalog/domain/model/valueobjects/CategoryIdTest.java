package br.edu.ddd.project.productcatalog.domain.model.valueobjects;

import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class CategoryIdTest {

    @Test
    void constructorShouldRequireNonNullValue() {
        assertThrows(NullPointerException.class, () -> new CategoryId(null));
    }

    @Test
    void generateShouldCreateUniqueIds() {
        CategoryId id1 = CategoryId.generate();
        CategoryId id2 = CategoryId.generate();
        assertNotNull(id1.value());
        assertNotNull(id2.value());
        assertNotEquals(id1, id2);
    }

    @Test
    void valueShouldReturnProvidedUuid() {
        UUID uuid = UUID.randomUUID();
        CategoryId id = new CategoryId(uuid);
        assertEquals(uuid, id.value());
    }
}
