/*
 * MaterialTest.java
 *
 * Version 1.0
 *
 * 2026-10-10
 */
package ua.oop.knight.model.ammunition.armor;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

class MaterialTest {

    @Test
    void displayNameIsHumanReadable() {
        assertEquals("chain mail", Material.CHAIN_MAIL.getDisplayName());
        assertEquals("steel", Material.STEEL.getDisplayName());
    }

    @ParameterizedTest
    @EnumSource(Material.class)
    void everyMaterialHasDisplayName(Material material) {
        assertFalse(material.getDisplayName().isBlank());
    }
}
