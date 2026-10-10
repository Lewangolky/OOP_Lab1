/*
 * AmmunitionTest.java
 *
 * Version 1.0
 *
 * 2026-10-10
 */
package ua.oop.knight.model.ammunition;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.math.BigDecimal;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import ua.oop.knight.model.EquipmentSlot;

class AmmunitionTest {

    private static final BigDecimal PRICE = new BigDecimal("10.50");

    @Test
    void constructorStoresTrimmedNameWeightAndPrice() {
        Ammunition item = new TestItem("  Rope ", 1.5, PRICE);

        assertEquals("Rope", item.getName());
        assertEquals(1.5, item.getWeight());
        assertEquals(PRICE, item.getPrice());
    }

    @Test
    void zeroPriceIsAllowed() {
        Ammunition item = new TestItem("Rope", 1.5, BigDecimal.ZERO);

        assertEquals(BigDecimal.ZERO, item.getPrice());
    }

    @Test
    void nullNameIsRejected() {
        assertThrows(NullPointerException.class,
                () -> new TestItem(null, 1.5, PRICE));
    }

    @ParameterizedTest
    @ValueSource(strings = {"", "   ", "\t"})
    void blankNameIsRejected(String name) {
        assertThrows(IllegalArgumentException.class,
                () -> new TestItem(name, 1.5, PRICE));
    }

    @ParameterizedTest
    @ValueSource(doubles = {0, -0.1, Double.NaN, Double.POSITIVE_INFINITY})
    void nonPositiveOrNonFiniteWeightIsRejected(double weight) {
        assertThrows(IllegalArgumentException.class,
                () -> new TestItem("Rope", weight, PRICE));
    }

    @Test
    void nullPriceIsRejected() {
        assertThrows(NullPointerException.class,
                () -> new TestItem("Rope", 1.5, null));
    }

    @Test
    void negativePriceIsRejected() {
        assertThrows(IllegalArgumentException.class,
                () -> new TestItem("Rope", 1.5, new BigDecimal("-0.01")));
    }

    @Test
    void typeNameIsSimpleClassName() {
        assertEquals("TestItem", new TestItem("Rope", 1.5, PRICE)
                .getTypeName());
    }

    @Test
    void toStringShowsTypeNameWeightPriceAndDetails() {
        Ammunition item = new TestItem("Rope", 1.5, PRICE);

        assertEquals("TestItem  Rope                   1.50 kg    10.50 gold"
                + "  test details", item.toString());
    }

    /**
     * Minimal concrete ammunition used to test the base class alone.
     */
    private static final class TestItem extends Ammunition {

        TestItem(String name, double weight, BigDecimal price) {
            super(name, weight, price);
        }

        @Override
        public EquipmentSlot getSlot() {
            return EquipmentSlot.BELT;
        }

        @Override
        protected String getDetails() {
            return "test details";
        }
    }
}
