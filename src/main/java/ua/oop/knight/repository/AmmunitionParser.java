/*
 * AmmunitionParser.java
 *
 * Version 1.0
 *
 * 2026-10-10
 */
package ua.oop.knight.repository;

import java.math.BigDecimal;
import java.util.Locale;
import java.util.Objects;

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
 * Converts a text record into a piece of ammunition.
 *
 * <p>A record consists of fields separated by {@value #SEPARATOR}:
 * <pre>
 * type;name;weight;price;&lt;type specific fields&gt;
 *
 * HELMET;name;weight;price;material;protection;visor
 * CUIRASS|GAUNTLETS|GREAVES;name;weight;price;material;protection
 * SHIELD;name;weight;price;material;protection;blockChance
 * SWORD;name;weight;price;damage;bladeLength
 * MACE;name;weight;price;damage;spiked
 * DAGGER;name;weight;price;damage
 * </pre>
 */
public class AmmunitionParser {

    /** Separator of the fields in a record. */
    public static final String SEPARATOR = ";";

    private static final int TYPE = 0;
    private static final int NAME = 1;
    private static final int WEIGHT = 2;
    private static final int PRICE = 3;
    private static final int MATERIAL = 4;
    private static final int PROTECTION = 5;
    private static final int ARMOR_EXTRA = 6;
    private static final int DAMAGE = 4;
    private static final int WEAPON_EXTRA = 5;

    /**
     * Parses a single record.
     *
     * @param record text record
     * @return the described item
     * @throws IllegalArgumentException if the record is malformed or
     *                                  describes an invalid item
     */
    public Ammunition parse(String record) {
        Objects.requireNonNull(record, "Record must not be null");
        String[] fields = record.split(SEPARATOR, -1);
        for (int i = 0; i < fields.length; i++) {
            fields[i] = fields[i].trim();
        }
        AmmunitionType type = AmmunitionType.fromCode(fields[TYPE]);
        if (fields.length != type.getFieldCount()) {
            throw new IllegalArgumentException(String.format(
                    "%s record must have %d fields, but has %d",
                    type, type.getFieldCount(), fields.length));
        }
        return create(type, fields);
    }

    private Ammunition create(AmmunitionType type, String[] fields) {
        String name = fields[NAME];
        double weight = parseDouble(fields[WEIGHT], "weight");
        BigDecimal price = parsePrice(fields[PRICE]);
        return switch (type) {
            case HELMET -> new Helmet(name, weight, price,
                    parseMaterial(fields), parseProtection(fields),
                    parseBoolean(fields[ARMOR_EXTRA], "visor"));
            case CUIRASS -> new Cuirass(name, weight, price,
                    parseMaterial(fields), parseProtection(fields));
            case GAUNTLETS -> new Gauntlets(name, weight, price,
                    parseMaterial(fields), parseProtection(fields));
            case GREAVES -> new Greaves(name, weight, price,
                    parseMaterial(fields), parseProtection(fields));
            case SHIELD -> new Shield(name, weight, price,
                    parseMaterial(fields), parseProtection(fields),
                    parseInt(fields[ARMOR_EXTRA], "block chance"));
            case SWORD -> new Sword(name, weight, price, parseDamage(fields),
                    parseInt(fields[WEAPON_EXTRA], "blade length"));
            case MACE -> new Mace(name, weight, price, parseDamage(fields),
                    parseBoolean(fields[WEAPON_EXTRA], "spiked"));
            case DAGGER -> new Dagger(name, weight, price,
                    parseDamage(fields));
        };
    }

    private Material parseMaterial(String[] fields) {
        String value = fields[MATERIAL];
        try {
            return Material.valueOf(value.toUpperCase(Locale.ROOT));
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException(
                    "Unknown material: " + value, e);
        }
    }

    private int parseProtection(String[] fields) {
        return parseInt(fields[PROTECTION], "protection");
    }

    private int parseDamage(String[] fields) {
        return parseInt(fields[DAMAGE], "damage");
    }

    private BigDecimal parsePrice(String value) {
        try {
            return new BigDecimal(value);
        } catch (NumberFormatException e) {
            throw invalidValue("price", value, e);
        }
    }

    private double parseDouble(String value, String field) {
        try {
            return Double.parseDouble(value);
        } catch (NumberFormatException e) {
            throw invalidValue(field, value, e);
        }
    }

    private int parseInt(String value, String field) {
        try {
            return Integer.parseInt(value);
        } catch (NumberFormatException e) {
            throw invalidValue(field, value, e);
        }
    }

    private boolean parseBoolean(String value, String field) {
        if ("true".equalsIgnoreCase(value)) {
            return true;
        }
        if ("false".equalsIgnoreCase(value)) {
            return false;
        }
        throw invalidValue(field, value, null);
    }

    private IllegalArgumentException invalidValue(String field, String value,
            Throwable cause) {
        return new IllegalArgumentException(
                "Invalid " + field + ": '" + value + "'", cause);
    }
}
