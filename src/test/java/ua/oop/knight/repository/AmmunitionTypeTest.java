/*
 * AmmunitionTypeTest.java
 *
 * Version 1.0
 *
 * 2026-10-10
 */
package ua.oop.knight.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class AmmunitionTypeTest {

    @ParameterizedTest
    @ValueSource(strings = {"HELMET", "helmet", " Helmet "})
    void fromCodeIgnoresCaseAndSurroundingSpaces(String code) {
        assertEquals(AmmunitionType.HELMET, AmmunitionType.fromCode(code));
    }

    @Test
    void fromCodeRejectsUnknownType() {
        IllegalArgumentException e = assertThrows(
                IllegalArgumentException.class,
                () -> AmmunitionType.fromCode("crossbow"));

        assertEquals("Unknown ammunition type: crossbow", e.getMessage());
    }

    @Test
    void fieldCountIncludesTypeAndCommonFields() {
        assertEquals(7, AmmunitionType.HELMET.getFieldCount());
        assertEquals(6, AmmunitionType.CUIRASS.getFieldCount());
        assertEquals(7, AmmunitionType.SHIELD.getFieldCount());
        assertEquals(6, AmmunitionType.SWORD.getFieldCount());
        assertEquals(5, AmmunitionType.DAGGER.getFieldCount());
    }
}
