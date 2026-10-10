/*
 * Sword.java
 *
 * Version 1.0
 *
 * 2026-10-10
 */
package ua.oop.knight.model.ammunition.weapon;

import java.math.BigDecimal;

/**
 * Bladed weapon, the main weapon of a knight.
 */
public final class Sword extends Weapon {

    private final int bladeLength;

    /**
     * Creates a sword.
     *
     * @param name        item name, must not be blank
     * @param weight      item weight in kilograms, must be positive
     * @param price       item price in gold coins, must not be negative
     * @param damage      damage points, must be positive
     * @param bladeLength blade length in centimeters, must be positive
     * @throws IllegalArgumentException if a value is out of range
     */
    public Sword(String name, double weight, BigDecimal price, int damage,
            int bladeLength) {
        super(name, weight, price, damage);
        if (bladeLength <= 0) {
            throw new IllegalArgumentException(
                    "Blade length must be positive: " + bladeLength);
        }
        this.bladeLength = bladeLength;
    }

    /**
     * Returns the blade length.
     *
     * @return blade length in centimeters
     */
    public int getBladeLength() {
        return bladeLength;
    }

    @Override
    protected String getDetails() {
        return super.getDetails() + ", blade " + bladeLength + " cm";
    }
}
