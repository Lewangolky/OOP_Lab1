/*
 * EquipmentSlotTest.java
 *
 * Version 1.0
 *
 * 2026-10-10
 */
package ua.oop.knight.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

class EquipmentSlotTest {

    @Test
    void displayNameIsHumanReadable() {
        assertEquals("Head", EquipmentSlot.HEAD.getDisplayName());
        assertEquals("Main hand", EquipmentSlot.MAIN_HAND.getDisplayName());
        assertEquals("Off hand", EquipmentSlot.OFF_HAND.getDisplayName());
    }

    @ParameterizedTest
    @EnumSource(EquipmentSlot.class)
    void everySlotHasDisplayName(EquipmentSlot slot) {
        assertFalse(slot.getDisplayName().isBlank());
    }
}
