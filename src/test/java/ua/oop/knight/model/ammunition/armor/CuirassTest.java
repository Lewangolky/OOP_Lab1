/*
 * CuirassTest.java
 *
 * Version 1.0
 *
 * 2026-10-10
 */
package ua.oop.knight.model.ammunition.armor;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.math.BigDecimal;

import org.junit.jupiter.api.Test;

import ua.oop.knight.model.EquipmentSlot;

class CuirassTest {

    private final Cuirass cuirass = new Cuirass("Milanese Cuirass", 12,
            new BigDecimal("450.00"), Material.STEEL, 60);

    @Test
    void cuirassIsWornOnBody() {
        assertEquals(EquipmentSlot.BODY, cuirass.getSlot());
    }

    @Test
    void cuirassKeepsArmorProperties() {
        assertEquals("Milanese Cuirass", cuirass.getName());
        assertEquals(60, cuirass.getProtection());
        assertEquals("protection 60, steel", cuirass.getDetails());
    }
}
