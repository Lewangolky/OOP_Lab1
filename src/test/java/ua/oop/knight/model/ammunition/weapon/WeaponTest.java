/*
 * WeaponTest.java
 *
 * Version 1.0
 *
 * 2026-10-10
 */
package ua.oop.knight.model.ammunition.weapon;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.math.BigDecimal;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import ua.oop.knight.model.EquipmentSlot;
import ua.oop.knight.model.ammunition.Ammunition;

/**
 * Checks the behaviour every kind of weapon inherits from {@link Weapon}.
 */
class WeaponTest {

    private static final BigDecimal PRICE = new BigDecimal("180.00");

    @Test
    void constructorStoresDamage() {
        Weapon weapon = new Mace("Morning Star", 2.5, PRICE, 42, true);

        assertEquals(42, weapon.getDamage());
    }

    @ParameterizedTest
    @ValueSource(ints = {0, -5})
    void nonPositiveDamageIsRejected(int damage) {
        assertThrows(IllegalArgumentException.class,
                () -> new Mace("Morning Star", 2.5, PRICE, damage, true));
    }

    @Test
    void invalidCommonPropertiesAreRejected() {
        assertThrows(IllegalArgumentException.class,
                () -> new Mace(" ", 2.5, PRICE, 42, true));
    }

    @Test
    void weaponIsHeldInMainHandByDefault() {
        Weapon weapon = new Mace("Morning Star", 2.5, PRICE, 42, true);

        assertEquals(EquipmentSlot.MAIN_HAND, weapon.getSlot());
    }

    @Test
    void weaponIsAmmunition() {
        Weapon weapon = new Mace("Morning Star", 2.5, PRICE, 42, true);

        assertInstanceOf(Ammunition.class, weapon);
    }
}
