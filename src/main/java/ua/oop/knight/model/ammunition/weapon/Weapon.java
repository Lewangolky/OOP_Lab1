/*
 * Weapon.java
 *
 * Version 1.0
 *
 * 2026-10-10
 */
package ua.oop.knight.model.ammunition.weapon;

import java.math.BigDecimal;

import ua.oop.knight.model.EquipmentSlot;
import ua.oop.knight.model.ammunition.Ammunition;

/**
 * Offensive ammunition. Every weapon deals a certain amount of damage
 * and by default is held in the main hand.
 */
public abstract class Weapon extends Ammunition {

    private final int damage;

    /**
     * Initializes the properties common to all weapons.
     *
     * @param name   item name, must not be blank
     * @param weight item weight in kilograms, must be positive
     * @param price  item price in gold coins, must not be negative
     * @param damage damage points, must be positive
     * @throws IllegalArgumentException if a value is out of range
     */
    protected Weapon(String name, double weight, BigDecimal price,
            int damage) {
        super(name, weight, price);
        if (damage <= 0) {
            throw new IllegalArgumentException(
                    "Damage must be positive: " + damage);
        }
        this.damage = damage;
    }

    /**
     * Returns the damage dealt by the weapon.
     *
     * @return damage points
     */
    public int getDamage() {
        return damage;
    }

    @Override
    public EquipmentSlot getSlot() {
        return EquipmentSlot.MAIN_HAND;
    }

    @Override
    protected String getDetails() {
        return "damage " + damage;
    }
}
