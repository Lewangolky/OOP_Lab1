/*
 * Mace.java
 *
 * Version 1.0
 *
 * 2026-10-10
 */
package ua.oop.knight.model.ammunition.weapon;

import java.math.BigDecimal;

/**
 * Blunt weapon effective against heavy armor. The head of a mace may
 * be covered with spikes.
 */
public final class Mace extends Weapon {

    private final boolean spiked;

    /**
     * Creates a mace.
     *
     * @param name   item name, must not be blank
     * @param weight item weight in kilograms, must be positive
     * @param price  item price in gold coins, must not be negative
     * @param damage damage points, must be positive
     * @param spiked whether the head of the mace has spikes
     */
    public Mace(String name, double weight, BigDecimal price, int damage,
            boolean spiked) {
        super(name, weight, price, damage);
        this.spiked = spiked;
    }

    /**
     * Tells whether the head of the mace has spikes.
     *
     * @return {@code true} for a spiked mace
     */
    public boolean isSpiked() {
        return spiked;
    }

    @Override
    protected String getDetails() {
        return super.getDetails() + (spiked ? ", spiked" : ", flanged");
    }
}
