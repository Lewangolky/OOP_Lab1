/*
 * ConsoleMenuTest.java
 *
 * Version 1.0
 *
 * 2026-10-10
 */
package ua.oop.knight.ui;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import ua.oop.knight.model.Knight;
import ua.oop.knight.model.ammunition.Ammunition;
import ua.oop.knight.service.AmmunitionService;
import ua.oop.knight.testutil.AmmunitionFixtures;

@ExtendWith(MockitoExtension.class)
class ConsoleMenuTest {

    private static final String NL = System.lineSeparator();

    @Mock
    private AmmunitionService service;

    private Knight knight;
    private Ammunition helmet;
    private Ammunition dagger;

    @BeforeEach
    void setUp() {
        knight = new Knight("Sir Galahad", 40);
        helmet = AmmunitionFixtures.helmet();
        dagger = AmmunitionFixtures.dagger();
        knight.equip(helmet);
        knight.equip(dagger);
    }

    @Test
    void exitOptionSaysFarewell() {
        String output = run("0");

        assertTrue(output.startsWith("Knight: " + knight + NL));
        assertTrue(output.contains("1 - Show equipment and total cost"));
        assertTrue(output.contains("Farewell!"));
        verifyNoInteractions(service);
    }

    @Test
    void menuStopsWhenInputEnds() {
        String output = run("");

        assertTrue(output.endsWith("> "));
        assertFalse(output.contains("Farewell!"));
    }

    @Test
    void unknownOptionIsReportedAndMenuContinues() {
        String output = run("9", "0");

        assertTrue(output.contains("Unknown option: 9"));
        assertTrue(output.contains("Farewell!"));
    }

    @Test
    void showEquipmentPrintsSlotsWeightAndCost() {
        when(service.calculateTotalCost(knight.getAmmunition()))
                .thenReturn(new BigDecimal("160"));

        String output = run(" 1 ", "0");

        assertTrue(output.contains("Equipment of Sir Galahad:"));
        assertTrue(output.contains("  Head:      " + helmet + NL));
        assertTrue(output.contains("  Belt:      " + dagger + NL));
        assertTrue(output.contains("Total weight: 2.90 of 40.00 kg"));
        assertTrue(output.contains("Total cost: 160.00 gold"));
    }

    @Test
    void sortByWeightPrintsItemsInReturnedOrder() {
        when(service.sortByWeight(knight.getAmmunition()))
                .thenReturn(List.of(dagger, helmet));

        String output = run("2", "0");

        assertTrue(output.contains("from the lightest to the heaviest:" + NL
                + "  " + dagger + NL + "  " + helmet + NL));
    }

    @Test
    void findByPriceRangePassesBoundsToService() {
        when(service.findByPriceRange(knight.getAmmunition(),
                new BigDecimal("100"), new BigDecimal("200.5")))
                .thenReturn(List.of(helmet));

        String output = run("3", "100", " 200.5 ", "0");

        assertTrue(output.contains("Min price: Max price: "));
        assertTrue(output.contains("Ammunition priced from 100 to 200.5:"
                + NL + "  " + helmet + NL));
        assertFalse(output.contains(dagger.getName()));
    }

    @Test
    void findByPriceRangeReportsWhenNothingFound() {
        when(service.findByPriceRange(any(), any(), any()))
                .thenReturn(List.of());

        String output = run("3", "1000", "2000", "0");

        assertTrue(output.contains("  nothing found"));
    }

    @Test
    void findByPriceRangePrintsServiceError() {
        when(service.findByPriceRange(any(), any(), any()))
                .thenThrow(new IllegalArgumentException("Bad range"));

        String output = run("3", "200", "100", "0");

        assertTrue(output.contains("Bad range"));
        assertTrue(output.contains("Farewell!"));
    }

    @Test
    void invalidMinPriceIsRejected() {
        String output = run("3", "cheap", "0");

        assertTrue(output.contains("Invalid price: cheap"));
        assertFalse(output.contains("Max price: "));
        verifyNoInteractions(service);
    }

    @Test
    void invalidMaxPriceIsRejected() {
        String output = run("3", "10", "a lot", "0");

        assertTrue(output.contains("Invalid price: a lot"));
        verifyNoInteractions(service);
    }

    @Test
    void inputEndingWhileReadingPriceSkipsSearch() {
        String afterMin = run("3");
        String afterMax = run("3", "10");

        assertFalse(afterMin.contains("Max price: "));
        assertTrue(afterMax.contains("Max price: "));
        assertTrue(afterMax.endsWith("> "));
        verifyNoInteractions(service);
    }

    @Test
    void nullArgumentsAreRejected() {
        InputStream in = new ByteArrayInputStream(new byte[0]);
        PrintStream out = new PrintStream(new ByteArrayOutputStream());

        assertThrows(NullPointerException.class,
                () -> new ConsoleMenu(null, service, in, out));
        assertThrows(NullPointerException.class,
                () -> new ConsoleMenu(knight, null, in, out));
        assertThrows(NullPointerException.class,
                () -> new ConsoleMenu(knight, service, null, out));
        assertThrows(NullPointerException.class,
                () -> new ConsoleMenu(knight, service, in, null));
    }

    @Test
    void realServiceSortsAmmunition() {
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        ConsoleMenu menu = new ConsoleMenu(knight, new AmmunitionService(),
                input("2", "0"), new PrintStream(buffer, true,
                        StandardCharsets.UTF_8));

        menu.run();

        String output = buffer.toString(StandardCharsets.UTF_8);
        assertTrue(output.indexOf(dagger.getName())
                < output.indexOf(helmet.getName()));
    }

    private String run(String... lines) {
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        PrintStream out = new PrintStream(buffer, true, StandardCharsets.UTF_8);

        new ConsoleMenu(knight, service, input(lines), out).run();

        return buffer.toString(StandardCharsets.UTF_8);
    }

    private static InputStream input(String... lines) {
        String text = String.join(NL, lines);
        return new ByteArrayInputStream(text.getBytes(StandardCharsets.UTF_8));
    }
}
