/*
 * EquipmentSlot.java
 *
 * Version 1.0
 *
 * 2026-10-10
 */
package ua.oop.knight.model;

/**
 * Place on a knight's body where a single piece of ammunition is worn.
 */
public enum EquipmentSlot {

    HEAD("Head"),
    BODY("Body"),
    HANDS("Hands"),
    LEGS("Legs"),
    MAIN_HAND("Main hand"),
    OFF_HAND("Off hand"),
    BELT("Belt");

    private final String displayName;

    EquipmentSlot(String displayName) {
        this.displayName = displayName;
    }

    /**
     * Returns a human readable slot name.
     *
     * @return slot name, e.g. "Main hand"
     */
    public String getDisplayName() {
        return displayName;
    }
}
