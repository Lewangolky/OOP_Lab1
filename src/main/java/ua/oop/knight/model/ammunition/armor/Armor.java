/*
 * Armor.java
 *
 * Version 1.0
 *
 * 2026-10-10
 */
package ua.oop.knight.model.ammunition.armor;

import java.math.BigDecimal;
import java.util.Objects;

import ua.oop.knight.model.ammunition.Ammunition;

/**
 * Protective ammunition. Every piece of armor is made of some material
 * and provides a certain level of protection.
 */
public abstract class Armor extends Ammunition {

    private final Material material;
    private final int protection;

    /**
     * Initializes the properties common to all armor.
     *
     * @param name       item name, must not be blank
     * @param weight     item weight in kilograms, must be positive
     * @param price      item price in gold coins, must not be negative
     * @param material   material the armor is made of
     * @param protection protection points, must not be negative
     * @throws IllegalArgumentException if a value is out of range
     * @throws NullPointerException     if an object argument is null
     */
    protected Armor(String name, double weight, BigDecimal price,
            Material material, int protection) {
        super(name, weight, price);
        if (protection < 0) {
            throw new IllegalArgumentException(
                    "Protection must not be negative: " + protection);
        }
        this.material = Objects.requireNonNull(material,
                "Material must not be null");
        this.protection = protection;
    }

    /**
     * Returns the material the armor is made of.
     *
     * @return material
     */
    public Material getMaterial() {
        return material;
    }

    /**
     * Returns the protection level.
     *
     * @return protection points
     */
    public int getProtection() {
        return protection;
    }

    @Override
    protected String getDetails() {
        return "protection " + protection + ", " + material.getDisplayName();
    }
}
