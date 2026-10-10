/*
 * AmmunitionServiceTest.java
 *
 * Version 1.0
 *
 * 2026-10-10
 */
package ua.oop.knight.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import ua.oop.knight.model.ammunition.Ammunition;
import ua.oop.knight.model.ammunition.weapon.Dagger;
import ua.oop.knight.testutil.AmmunitionFixtures;

class AmmunitionServiceTest {

    private final AmmunitionService service = new AmmunitionService();

    private final Ammunition helmet = AmmunitionFixtures.helmet();
    private final Ammunition cuirass = AmmunitionFixtures.cuirass();
    private final Ammunition gauntlets = AmmunitionFixtures.gauntlets();
    private final Ammunition greaves = AmmunitionFixtures.greaves();
    private final Ammunition sword = AmmunitionFixtures.sword();
    private final Ammunition shield = AmmunitionFixtures.shield();
    private final Ammunition dagger = AmmunitionFixtures.dagger();

    private final List<Ammunition> equipment = List.of(helmet, cuirass,
            gauntlets, greaves, sword, shield, dagger);

    @Test
    void totalCostIsSumOfPrices() {
        assertEquals(AmmunitionFixtures.FULL_SET_COST,
                service.calculateTotalCost(equipment));
    }

    @Test
    void totalCostOfNothingIsZero() {
        assertEquals(BigDecimal.ZERO, service.calculateTotalCost(List.of()));
    }

    @Test
    void totalCostOfNullIsRejected() {
        assertThrows(NullPointerException.class,
                () -> service.calculateTotalCost(null));
    }

    @Test
    void sortByWeightOrdersFromLightestToHeaviest() {
        List<Ammunition> sorted = service.sortByWeight(equipment);

        assertEquals(List.of(dagger, sword, gauntlets, helmet, greaves, shield,
                cuirass), sorted);
    }

    @Test
    void itemsOfEqualWeightAreSortedByName() {
        Ammunition stiletto = new Dagger("Stiletto", 0.4, BigDecimal.ONE, 10);
        Ammunition baselard = new Dagger("Baselard", 0.4, BigDecimal.ONE, 12);

        List<Ammunition> sorted = service.sortByWeight(
                List.of(stiletto, baselard));

        assertEquals(List.of(baselard, stiletto), sorted);
    }

    @Test
    void sortByWeightDoesNotChangeSource() {
        List<Ammunition> source = new ArrayList<>(equipment);

        service.sortByWeight(source);

        assertEquals(equipment, source);
    }

    @Test
    void sortByWeightOfNullIsRejected() {
        assertThrows(NullPointerException.class,
                () -> service.sortByWeight(null));
    }

    @Test
    void findByPriceRangeIncludesBounds() {
        List<Ammunition> found = service.findByPriceRange(equipment,
                new BigDecimal("80"), new BigDecimal("150"));

        assertEquals(List.of(helmet, gauntlets, greaves, shield), found);
    }

    @Test
    void findByPriceRangeWithEqualBoundsFindsExactPrice() {
        List<Ammunition> found = service.findByPriceRange(equipment,
                new BigDecimal("300"), new BigDecimal("300.0"));

        assertEquals(List.of(sword), found);
    }

    @Test
    void findByPriceRangeReturnsEmptyListWhenNothingMatches() {
        List<Ammunition> found = service.findByPriceRange(equipment,
                new BigDecimal("1000"), new BigDecimal("2000"));

        assertTrue(found.isEmpty());
    }

    @Test
    void findByPriceRangeRejectsInvertedRange() {
        IllegalArgumentException e = assertThrows(
                IllegalArgumentException.class,
                () -> service.findByPriceRange(equipment,
                        new BigDecimal("200"), new BigDecimal("100")));

        assertEquals("Min price 200 exceeds max price 100", e.getMessage());
    }

    @Test
    void findByPriceRangeRejectsNullArguments() {
        BigDecimal price = BigDecimal.TEN;

        assertThrows(NullPointerException.class,
                () -> service.findByPriceRange(null, price, price));
        assertThrows(NullPointerException.class,
                () -> service.findByPriceRange(equipment, null, price));
        assertThrows(NullPointerException.class,
                () -> service.findByPriceRange(equipment, price, null));
    }
}
