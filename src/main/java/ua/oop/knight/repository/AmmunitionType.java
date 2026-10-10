/*
 * AmmunitionType.java
 *
 * Version 1.0
 *
 * 2026-10-10
 */
package ua.oop.knight.repository;

import java.util.Locale;

/**
 * Kind of ammunition as written in the ammunition file, together with
 * the number of fields its record consists of.
 */
public enum AmmunitionType {

    HELMET(7),
    CUIRASS(6),
    GAUNTLETS(6),
    GREAVES(6),
    SHIELD(7),
    SWORD(6),
    MACE(6),
    DAGGER(5);

    private final int fieldCount;

    AmmunitionType(int fieldCount) {
        this.fieldCount = fieldCount;
    }

    /**
     * Returns the number of fields in a record of this kind, including
     * the type itself.
     *
     * @return field count
     */
    public int getFieldCount() {
        return fieldCount;
    }

    /**
     * Finds the type by its code, ignoring case.
     *
     * @param code type code, e.g. "helmet"
     * @return matching type
     * @throws IllegalArgumentException if there is no such type
     */
    public static AmmunitionType fromCode(String code) {
        try {
            return valueOf(code.trim().toUpperCase(Locale.ROOT));
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException(
                    "Unknown ammunition type: " + code, e);
        }
    }
}
