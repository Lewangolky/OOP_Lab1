/*
 * ConsoleMenu.java
 *
 * Version 1.0
 *
 * 2026-10-10
 */
package ua.oop.knight.ui;

import java.io.InputStream;
import java.io.PrintStream;
import java.math.BigDecimal;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.Scanner;

import ua.oop.knight.model.Knight;
import ua.oop.knight.model.ammunition.Ammunition;
import ua.oop.knight.service.AmmunitionService;

/**
 * Minimal text menu for inspecting the ammunition of a knight.
 */
public class ConsoleMenu {

    /** Option that shows the equipment and its cost. */
    public static final String SHOW_EQUIPMENT = "1";

    /** Option that sorts the ammunition by weight. */
    public static final String SORT_BY_WEIGHT = "2";

    /** Option that searches the ammunition by price range. */
    public static final String FIND_BY_PRICE = "3";

    /** Option that closes the menu. */
    public static final String EXIT = "0";

    private static final String MENU = String.join(System.lineSeparator(),
            "",
            SHOW_EQUIPMENT + " - Show equipment and total cost",
            SORT_BY_WEIGHT + " - Sort ammunition by weight",
            FIND_BY_PRICE + " - Find ammunition by price range",
            EXIT + " - Exit");

    private final Knight knight;
    private final AmmunitionService service;
    private final Scanner scanner;
    private final PrintStream out;

    /**
     * Creates a menu for the given knight.
     *
     * @param knight  knight whose ammunition is inspected
     * @param service calculations over the ammunition
     * @param in      source of the user's input
     * @param out     destination of the output
     */
    public ConsoleMenu(Knight knight, AmmunitionService service,
            InputStream in, PrintStream out) {
        this.knight = Objects.requireNonNull(knight,
                "Knight must not be null");
        this.service = Objects.requireNonNull(service,
                "Service must not be null");
        this.scanner = new Scanner(Objects.requireNonNull(in,
                "Input must not be null"));
        this.out = Objects.requireNonNull(out, "Output must not be null");
    }

    /**
     * Shows the menu and handles the chosen options until the user exits
     * or the input ends.
     */
    public void run() {
        out.println("Knight: " + knight);
        boolean running = true;
        while (running) {
            out.println(MENU);
            out.print("> ");
            String choice = readLine();
            running = (choice != null) && handle(choice.trim());
        }
    }

    private boolean handle(String choice) {
        switch (choice) {
        case SHOW_EQUIPMENT:
            showEquipment();
            return true;
        case SORT_BY_WEIGHT:
            showSortedByWeight();
            return true;
        case FIND_BY_PRICE:
            findByPriceRange();
            return true;
        case EXIT:
            out.println("Farewell!");
            return false;
        default:
            out.println("Unknown option: " + choice);
            return true;
        }
    }

    private void showEquipment() {
        List<Ammunition> items = knight.getAmmunition();
        out.println("Equipment of " + knight.getName() + ":");
        for (Ammunition item : items) {
            out.printf("  %-10s %s%n",
                    item.getSlot().getDisplayName() + ":", item);
        }
        out.printf(Locale.ROOT, "Total weight: %.2f of %.2f kg%n",
                knight.getTotalWeight(), knight.getMaxCarryWeight());
        out.printf(Locale.ROOT, "Total cost: %.2f gold%n",
                service.calculateTotalCost(items));
    }

    private void showSortedByWeight() {
        out.println("Ammunition from the lightest to the heaviest:");
        printItems(service.sortByWeight(knight.getAmmunition()));
    }

    private void findByPriceRange() {
        BigDecimal minPrice = readPrice("Min price: ");
        if (minPrice == null) {
            return;
        }
        BigDecimal maxPrice = readPrice("Max price: ");
        if (maxPrice == null) {
            return;
        }
        try {
            List<Ammunition> found = service.findByPriceRange(
                    knight.getAmmunition(), minPrice, maxPrice);
            out.println("Ammunition priced from " + minPrice + " to "
                    + maxPrice + ":");
            printItems(found);
        } catch (IllegalArgumentException e) {
            out.println(e.getMessage());
        }
    }

    private BigDecimal readPrice(String prompt) {
        out.print(prompt);
        String line = readLine();
        if (line == null) {
            return null;
        }
        try {
            return new BigDecimal(line.trim());
        } catch (NumberFormatException e) {
            out.println("Invalid price: " + line.trim());
            return null;
        }
    }

    private String readLine() {
        return scanner.hasNextLine() ? scanner.nextLine() : null;
    }

    private void printItems(List<Ammunition> items) {
        if (items.isEmpty()) {
            out.println("  nothing found");
        }
        for (Ammunition item : items) {
            out.println("  " + item);
        }
    }
}
