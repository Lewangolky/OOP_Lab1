/*
 * Material.java
 *
 * Version 1.0
 *
 * 2026-10-10
 */
package ua.oop.knight.model.ammunition.armor;

/**
 * Material a piece of armor is made of.
 */
public enum Material {

    LEATHER("leather"),
    CHAIN_MAIL("chain mail"),
    IRON("iron"),
    STEEL("steel");

    private final String displayName;

    Material(String displayName) {
        this.displayName = displayName;
    }

    /**
     * Returns a human readable material name.
     *
     * @return material name, e.g. "chain mail"
     */
    public String getDisplayName() {
        return displayName;
    }
}
