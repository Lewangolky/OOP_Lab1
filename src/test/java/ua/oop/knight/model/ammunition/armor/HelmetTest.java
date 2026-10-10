/*
 * HelmetTest.java
 *
 * Version 1.0
 *
 * 2026-10-10
 */
package ua.oop.knight.model.ammunition.armor;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.math.BigDecimal;

import org.junit.jupiter.api.Test;

import ua.oop.knight.model.EquipmentSlot;

class HelmetTest {

    private static final BigDecimal PRICE = new BigDecimal("120.00");

    @Test
    void helmetIsWornOnHead() {
        Helmet helmet = new Helmet("Bascinet", 2, PRICE, Material.STEEL, 30,
                true);

        assertEquals(EquipmentSlot.HEAD, helmet.getSlot());
    }

    @Test
    void helmetWithVisor() {
        Helmet helmet = new Helmet("Great Helm", 2.5, PRICE, Material.STEEL,
                35, true);

        assertTrue(helmet.hasVisor());
        assertEquals("protection 35, steel, with visor", helmet.getDetails());
    }

    @Test
    void helmetWithoutVisor() {
        Helmet helmet = new Helmet("Kettle Hat", 1.8, PRICE, Material.IRON,
                20, false);

        assertFalse(helmet.hasVisor());
        assertEquals("protection 20, iron, open face", helmet.getDetails());
    }

    @Test
    void toStringContainsTypeNameAndDetails() {
        Helmet helmet = new Helmet("Great Helm", 2.5, PRICE, Material.STEEL,
                35, true);

        assertEquals("Helmet    Great Helm             2.50 kg   120.00 gold"
                + "  protection 35, steel, with visor", helmet.toString());
    }
}
