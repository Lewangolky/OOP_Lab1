/*
 * Helmet.java
 *
 * Version 1.0
 *
 * 2026-10-10
 */
package ua.oop.knight.model.ammunition.armor;

import java.math.BigDecimal;

import ua.oop.knight.model.EquipmentSlot;

/**
 * Head armor. A helmet may have a visor that covers the face.
 */
public final class Helmet extends Armor {

    private final boolean visor;

    /**
     * Creates a helmet.
     *
     * @param name       item name, must not be blank
     * @param weight     item weight in kilograms, must be positive
     * @param price      item price in gold coins, must not be negative
     * @param material   material the helmet is made of
     * @param protection protection points, must not be negative
     * @param visor      whether the helmet has a visor
     */
    public Helmet(String name, double weight, BigDecimal price,
            Material material, int protection, boolean visor) {
        super(name, weight, price, material, protection);
        this.visor = visor;
    }

    /**
     * Tells whether the helmet has a visor.
     *
     * @return {@code true} if the face is covered by a visor
     */
    public boolean hasVisor() {
        return visor;
    }

    @Override
    public EquipmentSlot getSlot() {
        return EquipmentSlot.HEAD;
    }

    @Override
    protected String getDetails() {
        return super.getDetails() + (visor ? ", with visor" : ", open face");
    }
}
