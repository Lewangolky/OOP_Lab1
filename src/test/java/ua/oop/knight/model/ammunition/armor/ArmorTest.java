/*
 * ArmorTest.java
 *
 * Version 1.0
 *
 * 2026-10-10
 */
package ua.oop.knight.model.ammunition.armor;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.math.BigDecimal;

import org.junit.jupiter.api.Test;

import ua.oop.knight.model.ammunition.Ammunition;

/**
 * Checks the behaviour every kind of armor inherits from {@link Armor}.
 */
class ArmorTest {

    private static final BigDecimal PRICE = new BigDecimal("80");

    @Test
    void constructorStoresMaterialAndProtection() {
        Armor armor = new Gauntlets("Mittens", 1.2, PRICE,
                Material.CHAIN_MAIL, 12);

        assertEquals(Material.CHAIN_MAIL, armor.getMaterial());
        assertEquals(12, armor.getProtection());
    }

    @Test
    void zeroProtectionIsAllowed() {
        Armor armor = new Gauntlets("Gloves", 0.3, PRICE, Material.LEATHER, 0);

        assertEquals(0, armor.getProtection());
    }

    @Test
    void negativeProtectionIsRejected() {
        assertThrows(IllegalArgumentException.class,
                () -> new Gauntlets("Mittens", 1.2, PRICE, Material.IRON, -1));
    }

    @Test
    void nullMaterialIsRejected() {
        assertThrows(NullPointerException.class,
                () -> new Gauntlets("Mittens", 1.2, PRICE, null, 12));
    }

    @Test
    void invalidCommonPropertiesAreRejected() {
        assertThrows(IllegalArgumentException.class,
                () -> new Gauntlets("Mittens", -1, PRICE, Material.IRON, 12));
    }

    @Test
    void armorIsAmmunition() {
        Armor armor = new Greaves("Shin Guards", 3, PRICE, Material.IRON, 20);

        assertInstanceOf(Ammunition.class, armor);
    }

    @Test
    void detailsShowProtectionAndMaterial() {
        Armor armor = new Greaves("Shin Guards", 3, PRICE,
                Material.CHAIN_MAIL, 20);

        assertEquals("protection 20, chain mail", armor.getDetails());
    }
}
