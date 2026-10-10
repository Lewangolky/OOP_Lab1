/*
 * ShieldTest.java
 *
 * Version 1.0
 *
 * 2026-10-10
 */
package ua.oop.knight.model.ammunition.armor;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.math.BigDecimal;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import ua.oop.knight.model.EquipmentSlot;

class ShieldTest {

    private static final BigDecimal PRICE = new BigDecimal("95.00");

    @Test
    void shieldIsHeldInOffHand() {
        Shield shield = new Shield("Buckler", 1.5, PRICE, Material.IRON, 10,
                15);

        assertEquals(EquipmentSlot.OFF_HAND, shield.getSlot());
    }

    @ParameterizedTest
    @ValueSource(ints = {0, 25, Shield.MAX_BLOCK_CHANCE})
    void blockChanceWithinRangeIsAccepted(int blockChance) {
        Shield shield = new Shield("Buckler", 1.5, PRICE, Material.IRON, 10,
                blockChance);

        assertEquals(blockChance, shield.getBlockChance());
    }

    @ParameterizedTest
    @ValueSource(ints = {-1, Shield.MAX_BLOCK_CHANCE + 1})
    void blockChanceOutOfRangeIsRejected(int blockChance) {
        assertThrows(IllegalArgumentException.class,
                () -> new Shield("Buckler", 1.5, PRICE, Material.IRON, 10,
                        blockChance));
    }

    @Test
    void detailsShowBlockChance() {
        Shield shield = new Shield("Heater Shield", 4.5, PRICE, Material.IRON,
                30, 25);

        assertEquals("protection 30, iron, block 25%", shield.getDetails());
    }
}
