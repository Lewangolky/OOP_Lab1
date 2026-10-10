/*
 * GreavesTest.java
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

class GreavesTest {

    private final Greaves greaves = new Greaves("Plate Greaves", 4.2,
            new BigDecimal("150.00"), Material.IRON, 25);

    @Test
    void greavesAreWornOnLegs() {
        assertEquals(EquipmentSlot.LEGS, greaves.getSlot());
    }

    @Test
    void greavesKeepArmorProperties() {
        assertEquals(new BigDecimal("150.00"), greaves.getPrice());
        assertEquals("protection 25, iron", greaves.getDetails());
    }
}
