/*
 * Ammunition.java
 *
 * Version 1.0
 *
 * 2026-10-10
 */
package ua.oop.knight.model.ammunition;

import java.math.BigDecimal;
import java.util.Locale;
import java.util.Objects;

import ua.oop.knight.model.EquipmentSlot;

/**
 * Base class for every piece of a knight's ammunition.
 *
 * <p>Holds the properties shared by all items (name, weight and price)
 * and lets each subclass decide which slot it occupies and which
 * details describe it.
 */
public abstract class Ammunition {

    /** Layout of a single item line: type, name, weight, price, details. */
    private static final String FORMAT = "%-9s %-20s %6.2f kg %8.2f gold  %s";

    private final String name;
    private final double weight;
    private final BigDecimal price;

    /**
     * Initializes the properties common to all ammunition.
     *
     * @param name   item name, must not be blank
     * @param weight item weight in kilograms, must be positive
     * @param price  item price in gold coins, must not be negative
     * @throws IllegalArgumentException if a value is out of range
     * @throws NullPointerException     if name or price is null
     */
    protected Ammunition(String name, double weight, BigDecimal price) {
        Objects.requireNonNull(name, "Name must not be null");
        Objects.requireNonNull(price, "Price must not be null");
        if (name.isBlank()) {
            throw new IllegalArgumentException("Name must not be blank");
        }
        if (!(weight > 0) || Double.isInfinite(weight)) {
            throw new IllegalArgumentException(
                    "Weight must be a positive number: " + weight);
        }
        if (price.signum() < 0) {
            throw new IllegalArgumentException(
                    "Price must not be negative: " + price);
        }
        this.name = name.trim();
        this.weight = weight;
        this.price = price;
    }

    /**
     * Returns the item name.
     *
     * @return item name
     */
    public String getName() {
        return name;
    }

    /**
     * Returns the item weight.
     *
     * @return weight in kilograms
     */
    public double getWeight() {
        return weight;
    }

    /**
     * Returns the item price.
     *
     * @return price in gold coins
     */
    public BigDecimal getPrice() {
        return price;
    }

    /**
     * Returns a short name of the item kind, e.g. "Helmet".
     *
     * @return item kind
     */
    public String getTypeName() {
        return getClass().getSimpleName();
    }

    /**
     * Returns the slot of a knight's body this item occupies.
     *
     * @return equipment slot
     */
    public abstract EquipmentSlot getSlot();

    /**
     * Returns the characteristics specific to the item kind.
     *
     * @return comma separated details, e.g. "damage 40"
     */
    protected abstract String getDetails();

    @Override
    public String toString() {
        return String.format(Locale.ROOT, FORMAT,
                getTypeName(), name, weight, price, getDetails());
    }
}
