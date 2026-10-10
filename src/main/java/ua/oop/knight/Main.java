/*
 * Main.java
 *
 * Version 1.0
 *
 * 2026-10-10
 */
package ua.oop.knight;

import java.io.InputStream;
import java.io.PrintStream;
import java.nio.file.Path;
import java.nio.file.Paths;

import ua.oop.knight.config.KnightConfig;
import ua.oop.knight.exception.KnightException;
import ua.oop.knight.model.Knight;
import ua.oop.knight.repository.AmmunitionParser;
import ua.oop.knight.repository.CsvAmmunitionRepository;
import ua.oop.knight.service.AmmunitionService;
import ua.oop.knight.service.EquipmentService;
import ua.oop.knight.ui.ConsoleMenu;

/**
 * Entry point of the application. Reads the configuration, equips the
 * knight and opens the console menu.
 */
public final class Main {

    /** Configuration file used when no path is given on the command line. */
    static final Path DEFAULT_CONFIG = Paths.get("config", "knight.properties");

    private Main() {
    }

    /**
     * Starts the application.
     *
     * @param args optional path to the configuration file
     */
    public static void main(String[] args) {
        run(args, System.in, System.out);
    }

    /**
     * Runs the application with the given console streams.
     *
     * @param args optional path to the configuration file
     * @param in   source of the user's input
     * @param out  destination of the output
     */
    static void run(String[] args, InputStream in, PrintStream out) {
        Path configFile = (args.length > 0) ? Paths.get(args[0])
                : DEFAULT_CONFIG;
        try {
            KnightConfig config = KnightConfig.load(configFile);
            Knight knight = new Knight(config.getKnightName(),
                    config.getMaxCarryWeight());
            EquipmentService equipmentService = new EquipmentService(
                    new CsvAmmunitionRepository(config.getAmmunitionFile(),
                            new AmmunitionParser()));
            equipmentService.equip(knight);
            new ConsoleMenu(knight, new AmmunitionService(), in, out).run();
        } catch (KnightException e) {
            out.println("Error: " + e.getMessage());
        }
    }
}
