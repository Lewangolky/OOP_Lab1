/*
 * AmmunitionFixtures.java
 *
 * Version 1.0
 *
 * 2026-10-10
 */
package ua.oop.knight.testutil;

import java.math.BigDecimal;
import java.util.List;

import ua.oop.knight.model.ammunition.Ammunition;
import ua.oop.knight.model.ammunition.armor.Cuirass;
import ua.oop.knight.model.ammunition.armor.Gauntlets;
import ua.oop.knight.model.ammunition.armor.Greaves;
import ua.oop.knight.model.ammunition.armor.Helmet;
import ua.oop.knight.model.ammunition.armor.Material;
import ua.oop.knight.model.ammunition.armor.Shield;
import ua.oop.knight.model.ammunition.weapon.Dagger;
import ua.oop.knight.model.ammunition.weapon.Mace;
import ua.oop.knight.model.ammunition.weapon.Sword;

/**
 * Ready-made ammunition shared by the tests.
 */
public final class AmmunitionFixtures {

    /** Total weight of {@link #fullSet()}, in kilograms. */
    public static final double FULL_SET_WEIGHT = 27.0;

    /** Total price of {@link #fullSet()}, in gold. */
    public static final BigDecimal FULL_SET_COST = new BigDecimal("1235.00");

    private AmmunitionFixtures() {
    }

    public static Helmet helmet() {
        return new Helmet("Great Helm", 2.5, new BigDecimal("120.00"),
                Material.STEEL, 35, true);
    }

    public static Cuirass cuirass() {
        return new Cuirass("Milanese Cuirass", 12.0,
                new BigDecimal("450.00"), Material.STEEL, 60);
    }

    public static Gauntlets gauntlets() {
        return new Gauntlets("Hourglass Gauntlets", 1.8,
                new BigDecimal("80.00"), Material.STEEL, 15);
    }

    public static Greaves greaves() {
        return new Greaves("Plate Greaves", 4.2, new BigDecimal("150.00"),
                Material.IRON, 25);
    }

    public static Shield shield() {
        return new Shield("Heater Shield", 4.5, new BigDecimal("95.00"),
                Material.IRON, 30, 25);
    }

    public static Sword sword() {
        return new Sword("Longsword", 1.6, new BigDecimal("300.00"), 45, 95);
    }

    public static Mace mace() {
        return new Mace("Flanged Mace", 2.3, new BigDecimal("180.00"), 40,
                false);
    }

    public static Dagger dagger() {
        return new Dagger("Rondel Dagger", 0.4, new BigDecimal("40.00"), 15);
    }

    /**
     * Returns one item for every equipment slot, in the slot order.
     *
     * @return helmet, cuirass, gauntlets, greaves, sword, shield, dagger
     */
    public static List<Ammunition> fullSet() {
        return List.of(helmet(), cuirass(), gauntlets(), greaves(), sword(),
                shield(), dagger());
    }
}
