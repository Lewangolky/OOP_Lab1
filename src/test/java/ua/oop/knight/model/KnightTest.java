/*
 * KnightTest.java
 *
 * Version 1.0
 *
 * 2026-10-10
 */
package ua.oop.knight.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import ua.oop.knight.exception.OverloadException;
import ua.oop.knight.model.ammunition.Ammunition;
import ua.oop.knight.testutil.AmmunitionFixtures;

class KnightTest {

    private static final double MAX_WEIGHT = 30;

    private Knight knight;

    @BeforeEach
    void setUp() {
        knight = new Knight("Sir Test", MAX_WEIGHT);
    }

    @Test
    void constructorStoresTrimmedNameAndMaxCarryWeight() {
        Knight lancelot = new Knight(" Sir Lancelot ", 45.5);

        assertEquals("Sir Lancelot", lancelot.getName());
        assertEquals(45.5, lancelot.getMaxCarryWeight());
    }

    @Test
    void nullNameIsRejected() {
        assertThrows(NullPointerException.class, () -> new Knight(null, 30));
    }

    @Test
    void blankNameIsRejected() {
        assertThrows(IllegalArgumentException.class,
                () -> new Knight("  ", 30));
    }

    @ParameterizedTest
    @ValueSource(doubles = {0, -1, Double.NaN, Double.POSITIVE_INFINITY})
    void invalidMaxCarryWeightIsRejected(double maxCarryWeight) {
        assertThrows(IllegalArgumentException.class,
                () -> new Knight("Sir Test", maxCarryWeight));
    }

    @Test
    void newKnightHasNoAmmunition() {
        assertTrue(knight.getAmmunition().isEmpty());
        assertEquals(0, knight.getTotalWeight());
    }

    @Test
    void equipPutsItemIntoItsSlot() {
        Ammunition helmet = item(EquipmentSlot.HEAD, 2.5);

        Optional<Ammunition> replaced = knight.equip(helmet);

        assertTrue(replaced.isEmpty());
        assertSame(helmet, knight.getItem(EquipmentSlot.HEAD).orElseThrow());
    }

    @Test
    void equipReplacesItemInSameSlot() {
        Ammunition oldSword = item(EquipmentSlot.MAIN_HAND, 1.6);
        Ammunition newSword = item(EquipmentSlot.MAIN_HAND, 1.8);
        knight.equip(oldSword);

        Optional<Ammunition> replaced = knight.equip(newSword);

        assertSame(oldSword, replaced.orElseThrow());
        assertEquals(List.of(newSword), knight.getAmmunition());
        assertEquals(1.8, knight.getTotalWeight());
    }

    @Test
    void totalWeightIsSumOfItemWeights() {
        knight.equip(item(EquipmentSlot.HEAD, 2.5));
        knight.equip(item(EquipmentSlot.BODY, 12));
        knight.equip(item(EquipmentSlot.BELT, 0.5));

        assertEquals(15.0, knight.getTotalWeight(), 1e-9);
    }

    @Test
    void equipThrowsWhenItemIsTooHeavy() {
        Ammunition cuirass = item(EquipmentSlot.BODY, 25);
        Ammunition helmet = item(EquipmentSlot.HEAD, 6);
        knight.equip(cuirass);

        OverloadException e = assertThrows(OverloadException.class,
                () -> knight.equip(helmet));

        assertEquals("Sir Test cannot carry HEAD item: 31.00 kg exceeds"
                + " the limit of 30.00 kg", e.getMessage());
        assertEquals(List.of(cuirass), knight.getAmmunition());
    }

    @Test
    void equipAllowsExactlyMaxCarryWeight() {
        knight.equip(item(EquipmentSlot.BODY, 20));
        knight.equip(item(EquipmentSlot.LEGS, 10));

        assertEquals(MAX_WEIGHT, knight.getTotalWeight());
    }

    @Test
    void equipToleratesFloatingPointRounding() {
        Knight squire = new Knight("Squire", 0.3);
        squire.equip(item(EquipmentSlot.HEAD, 0.1));

        squire.equip(item(EquipmentSlot.HANDS, 0.2));

        assertEquals(2, squire.getAmmunition().size());
    }

    @Test
    void replacedItemWeightIsNotCountedTwice() {
        knight.equip(item(EquipmentSlot.HEAD, 10));
        knight.equip(item(EquipmentSlot.BODY, 20));

        knight.equip(item(EquipmentSlot.BODY, 15));

        assertEquals(25, knight.getTotalWeight());
        assertThrows(OverloadException.class,
                () -> knight.equip(item(EquipmentSlot.BODY, 25)));
    }

    @Test
    void equipNullIsRejected() {
        assertThrows(NullPointerException.class, () -> knight.equip(null));
    }

    @Test
    void unequipRemovesItemFromSlot() {
        Ammunition shield = item(EquipmentSlot.OFF_HAND, 4.5);
        knight.equip(shield);

        Optional<Ammunition> removed = knight.unequip(EquipmentSlot.OFF_HAND);

        assertSame(shield, removed.orElseThrow());
        assertTrue(knight.getItem(EquipmentSlot.OFF_HAND).isEmpty());
        assertEquals(0, knight.getTotalWeight());
    }

    @Test
    void unequipFreeSlotReturnsEmpty() {
        assertTrue(knight.unequip(EquipmentSlot.BELT).isEmpty());
    }

    @Test
    void nullSlotIsRejected() {
        assertThrows(NullPointerException.class, () -> knight.unequip(null));
        assertThrows(NullPointerException.class, () -> knight.getItem(null));
    }

    @Test
    void ammunitionIsListedInSlotOrder() {
        Knight galahad = new Knight("Sir Galahad", 40);
        List<Ammunition> fullSet = AmmunitionFixtures.fullSet();
        for (int i = fullSet.size() - 1; i >= 0; i--) {
            galahad.equip(fullSet.get(i));
        }

        assertEquals(fullSet, galahad.getAmmunition());
    }

    @Test
    void ammunitionListCannotBeModified() {
        knight.equip(item(EquipmentSlot.HEAD, 2));
        List<Ammunition> ammunition = knight.getAmmunition();

        assertThrows(UnsupportedOperationException.class,
                () -> ammunition.add(item(EquipmentSlot.BODY, 10)));
    }

    @Test
    void toStringShowsNameItemCountAndWeight() {
        knight.equip(item(EquipmentSlot.HEAD, 2.5));

        assertEquals("Sir Test (items: 1, weight: 2.50/30.00 kg)",
                knight.toString());
    }

    private static Ammunition item(EquipmentSlot slot, double weight) {
        Ammunition item = mock(Ammunition.class);
        when(item.getSlot()).thenReturn(slot);
        when(item.getWeight()).thenReturn(weight);
        when(item.getName()).thenReturn(slot + " item");
        return item;
    }
}
