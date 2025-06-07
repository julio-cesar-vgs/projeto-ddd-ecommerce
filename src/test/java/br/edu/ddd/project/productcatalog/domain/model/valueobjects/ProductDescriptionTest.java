package br.edu.ddd.project.productcatalog.domain.model.valueobjects;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ProductDescriptionTest {

    @Test
    void constructorShouldRequireNonNullName() {
        assertThrows(NullPointerException.class, () -> new ProductDescription(null, "short", "full"));
    }

    @Test
    void constructorShouldRejectEmptyName() {
        assertThrows(IllegalArgumentException.class, () -> new ProductDescription("   ", "short", "full"));
    }

    @Test
    void validDescriptionShouldStoreAllValues() {
        ProductDescription desc = new ProductDescription("Name", "short", "full");
        assertEquals("Name", desc.name());
        assertEquals("short", desc.shortDescription());
        assertEquals("full", desc.fullDescription());
    }
}
