/*
 * MaceTest.java
 *
 * Version 1.0
 *
 * 2026-10-10
 */
package ua.oop.knight.model.ammunition.weapon;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.math.BigDecimal;

import org.junit.jupiter.api.Test;

import ua.oop.knight.model.EquipmentSlot;

class MaceTest {

    private static final BigDecimal PRICE = new BigDecimal("180.00");

    @Test
    void maceIsHeldInMainHand() {
        Mace mace = new Mace("Flanged Mace", 2.3, PRICE, 40, false);

        assertEquals(EquipmentSlot.MAIN_HAND, mace.getSlot());
    }

    @Test
    void spikedMace() {
        Mace mace = new Mace("Morning Star", 2.5, PRICE, 42, true);

        assertTrue(mace.isSpiked());
        assertEquals("damage 42, spiked", mace.getDetails());
    }

    @Test
    void flangedMace() {
        Mace mace = new Mace("Flanged Mace", 2.3, PRICE, 40, false);

        assertFalse(mace.isSpiked());
        assertEquals("damage 40, flanged", mace.getDetails());
    }
}
