/*
 * Dagger.java
 *
 * Version 1.0
 *
 * 2026-10-10
 */
package ua.oop.knight.model.ammunition.weapon;

import java.math.BigDecimal;

import ua.oop.knight.model.EquipmentSlot;

/**
 * Short backup weapon. Unlike other weapons it is carried on the belt,
 * so it does not occupy the main hand.
 */
public final class Dagger extends Weapon {

    /**
     * Creates a dagger.
     *
     * @param name   item name, must not be blank
     * @param weight item weight in kilograms, must be positive
     * @param price  item price in gold coins, must not be negative
     * @param damage damage points, must be positive
     */
    public Dagger(String name, double weight, BigDecimal price, int damage) {
        super(name, weight, price, damage);
    }

    @Override
    public EquipmentSlot getSlot() {
        return EquipmentSlot.BELT;
    }
}
