/*
 * Shield.java
 *
 * Version 1.0
 *
 * 2026-10-10
 */
package ua.oop.knight.model.ammunition.armor;

import java.math.BigDecimal;

import ua.oop.knight.model.EquipmentSlot;

/**
 * Armor held in the off hand. Besides passive protection a shield
 * gives a chance to block an incoming blow completely.
 */
public final class Shield extends Armor {

    /** Upper bound of the block chance, in percent. */
    public static final int MAX_BLOCK_CHANCE = 100;

    private final int blockChance;

    /**
     * Creates a shield.
     *
     * @param name        item name, must not be blank
     * @param weight      item weight in kilograms, must be positive
     * @param price       item price in gold coins, must not be negative
     * @param material    material the shield is made of
     * @param protection  protection points, must not be negative
     * @param blockChance chance to block a blow, from 0 to 100 percent
     * @throws IllegalArgumentException if the block chance is out of range
     */
    public Shield(String name, double weight, BigDecimal price,
            Material material, int protection, int blockChance) {
        super(name, weight, price, material, protection);
        if (blockChance < 0 || blockChance > MAX_BLOCK_CHANCE) {
            throw new IllegalArgumentException(
                    "Block chance must be within 0..100: " + blockChance);
        }
        this.blockChance = blockChance;
    }

    /**
     * Returns the chance to block a blow.
     *
     * @return block chance in percent
     */
    public int getBlockChance() {
        return blockChance;
    }

    @Override
    public EquipmentSlot getSlot() {
        return EquipmentSlot.OFF_HAND;
    }

    @Override
    protected String getDetails() {
        return super.getDetails() + ", block " + blockChance + "%";
    }
}
