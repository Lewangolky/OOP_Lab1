/*
 * Cuirass.java
 *
 * Version 1.0
 *
 * 2026-10-10
 */
package ua.oop.knight.model.ammunition.armor;

import java.math.BigDecimal;

import ua.oop.knight.model.EquipmentSlot;

/**
 * Body armor that protects the chest and the back.
 */
public final class Cuirass extends Armor {

    /**
     * Creates a cuirass.
     *
     * @param name       item name, must not be blank
     * @param weight     item weight in kilograms, must be positive
     * @param price      item price in gold coins, must not be negative
     * @param material   material the cuirass is made of
     * @param protection protection points, must not be negative
     */
    public Cuirass(String name, double weight, BigDecimal price,
            Material material, int protection) {
        super(name, weight, price, material, protection);
    }

    @Override
    public EquipmentSlot getSlot() {
        return EquipmentSlot.BODY;
    }
}
