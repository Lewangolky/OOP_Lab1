/*
 * Knight.java
 *
 * Version 1.0
 *
 * 2026-10-10
 */
package ua.oop.knight.model;

import java.util.EnumMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

import ua.oop.knight.exception.OverloadException;
import ua.oop.knight.model.ammunition.Ammunition;

/**
 * Knight who wears ammunition.
 *
 * <p>Every equipment slot holds at most one item, and the total weight
 * of the ammunition never exceeds the weight the knight can carry.
 */
public class Knight {

    /** Tolerance for floating point weight comparison, in kilograms. */
    private static final double WEIGHT_EPSILON = 1e-9;

    private final String name;
    private final double maxCarryWeight;
    private final Map<EquipmentSlot, Ammunition> equipment =
            new EnumMap<>(EquipmentSlot.class);

    /**
     * Creates a knight without any ammunition.
     *
     * @param name           knight's name, must not be blank
     * @param maxCarryWeight maximum weight of the ammunition the knight
     *                       can carry, in kilograms, must be positive
     * @throws IllegalArgumentException if a value is out of range
     */
    public Knight(String name, double maxCarryWeight) {
        Objects.requireNonNull(name, "Name must not be null");
        if (name.isBlank()) {
            throw new IllegalArgumentException("Name must not be blank");
        }
        if (!(maxCarryWeight > 0) || Double.isInfinite(maxCarryWeight)) {
            throw new IllegalArgumentException(
                    "Max carry weight must be a positive number: "
                    + maxCarryWeight);
        }
        this.name = name.trim();
        this.maxCarryWeight = maxCarryWeight;
    }

    /**
     * Returns the knight's name.
     *
     * @return name
     */
    public String getName() {
        return name;
    }

    /**
     * Returns the maximum weight the knight can carry.
     *
     * @return weight in kilograms
     */
    public double getMaxCarryWeight() {
        return maxCarryWeight;
    }

    /**
     * Puts the item on the knight. An item already worn in the same slot
     * is taken off.
     *
     * @param item item to wear
     * @return the item taken off, or an empty optional if the slot was free
     * @throws OverloadException if the knight cannot carry the item
     */
    public Optional<Ammunition> equip(Ammunition item) {
        Objects.requireNonNull(item, "Item must not be null");
        EquipmentSlot slot = item.getSlot();
        Ammunition current = equipment.get(slot);
        double freedWeight = (current == null) ? 0 : current.getWeight();
        double newWeight = getTotalWeight() - freedWeight + item.getWeight();
        if (newWeight - maxCarryWeight > WEIGHT_EPSILON) {
            throw new OverloadException(String.format(Locale.ROOT,
                    "%s cannot carry %s: %.2f kg exceeds the limit of %.2f kg",
                    name, item.getName(), newWeight, maxCarryWeight));
        }
        return Optional.ofNullable(equipment.put(slot, item));
    }

    /**
     * Takes off the item worn in the given slot.
     *
     * @param slot slot to free
     * @return the item taken off, or an empty optional if the slot was free
     */
    public Optional<Ammunition> unequip(EquipmentSlot slot) {
        Objects.requireNonNull(slot, "Slot must not be null");
        return Optional.ofNullable(equipment.remove(slot));
    }

    /**
     * Returns the item worn in the given slot.
     *
     * @param slot slot to look into
     * @return the item, or an empty optional if the slot is free
     */
    public Optional<Ammunition> getItem(EquipmentSlot slot) {
        Objects.requireNonNull(slot, "Slot must not be null");
        return Optional.ofNullable(equipment.get(slot));
    }

    /**
     * Returns all the ammunition worn by the knight in the slot order.
     *
     * @return unmodifiable list of items
     */
    public List<Ammunition> getAmmunition() {
        return List.copyOf(equipment.values());
    }

    /**
     * Returns the total weight of the worn ammunition.
     *
     * @return weight in kilograms
     */
    public double getTotalWeight() {
        return equipment.values().stream()
                .mapToDouble(Ammunition::getWeight)
                .sum();
    }

    @Override
    public String toString() {
        return String.format(Locale.ROOT,
                "%s (items: %d, weight: %.2f/%.2f kg)", name,
                equipment.size(), getTotalWeight(), maxCarryWeight);
    }
}
