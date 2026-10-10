/*
 * DaggerTest.java
 *
 * Version 1.0
 *
 * 2026-10-10
 */
package ua.oop.knight.model.ammunition.weapon;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.math.BigDecimal;

import org.junit.jupiter.api.Test;

import ua.oop.knight.model.EquipmentSlot;

class DaggerTest {

    private final Dagger dagger = new Dagger("Rondel Dagger", 0.4,
            new BigDecimal("40.00"), 15);

    @Test
    void daggerIsCarriedOnBeltInsteadOfMainHand() {
        assertEquals(EquipmentSlot.BELT, dagger.getSlot());
    }

    @Test
    void detailsShowDamage() {
        assertEquals("damage 15", dagger.getDetails());
    }

    @Test
    void toStringContainsTypeNameAndDetails() {
        assertEquals("Dagger    Rondel Dagger          0.40 kg    40.00 gold"
                + "  damage 15", dagger.toString());
    }
}
