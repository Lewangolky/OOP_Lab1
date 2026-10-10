/*
 * SwordTest.java
 *
 * Version 1.0
 *
 * 2026-10-10
 */
package ua.oop.knight.model.ammunition.weapon;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.math.BigDecimal;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import ua.oop.knight.model.EquipmentSlot;

class SwordTest {

    private static final BigDecimal PRICE = new BigDecimal("300.00");

    @Test
    void swordIsHeldInMainHand() {
        Sword sword = new Sword("Longsword", 1.6, PRICE, 45, 95);

        assertEquals(EquipmentSlot.MAIN_HAND, sword.getSlot());
    }

    @Test
    void constructorStoresBladeLength() {
        Sword sword = new Sword("Longsword", 1.6, PRICE, 45, 95);

        assertEquals(95, sword.getBladeLength());
        assertEquals(45, sword.getDamage());
    }

    @ParameterizedTest
    @ValueSource(ints = {0, -10})
    void nonPositiveBladeLengthIsRejected(int bladeLength) {
        assertThrows(IllegalArgumentException.class,
                () -> new Sword("Longsword", 1.6, PRICE, 45, bladeLength));
    }

    @Test
    void detailsShowDamageAndBladeLength() {
        Sword sword = new Sword("Longsword", 1.6, PRICE, 45, 95);

        assertEquals("damage 45, blade 95 cm", sword.getDetails());
    }
}
