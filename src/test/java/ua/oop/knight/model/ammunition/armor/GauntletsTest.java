/*
 * GauntletsTest.java
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

class GauntletsTest {

    private final Gauntlets gauntlets = new Gauntlets("Hourglass Gauntlets",
            1.8, new BigDecimal("80.00"), Material.STEEL, 15);

    @Test
    void gauntletsAreWornOnHands() {
        assertEquals(EquipmentSlot.HANDS, gauntlets.getSlot());
    }

    @Test
    void gauntletsKeepArmorProperties() {
        assertEquals(1.8, gauntlets.getWeight());
        assertEquals(Material.STEEL, gauntlets.getMaterial());
        assertEquals("protection 15, steel", gauntlets.getDetails());
    }
}
