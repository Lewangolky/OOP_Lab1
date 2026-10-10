/*
 * MainTest.java
 *
 * Version 1.0
 *
 * 2026-10-10
 */
package ua.oop.knight;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

class MainTest {

    private static final String NL = System.lineSeparator();

    @TempDir
    Path tempDir;

    @Test
    void defaultConfigurationIsUsedWithoutArguments() {
        String output = run(new String[0], "1", "0");

        assertTrue(output.startsWith("Knight: Sir Galahad (items: 7"));
        assertTrue(output.contains("Total cost: 1235.00 gold"));
        assertTrue(output.contains("Farewell!"));
    }

    @Test
    void defaultConfigurationIsInConfigDirectory() {
        assertEquals(Paths.get("config", "knight.properties"),
                Main.DEFAULT_CONFIG);
    }

    @Test
    void configurationPathIsTakenFromFirstArgument() throws IOException {
        Path config = writeConfig("Sir Bors", "10",
                "SWORD;Arming Sword;1.2;200;40;80");

        String output = run(new String[] {config.toString()}, "1", "0");

        assertTrue(output.startsWith("Knight: Sir Bors (items: 1"));
        assertTrue(output.contains("Arming Sword"));
    }

    @Test
    void missingConfigurationIsReported() {
        Path config = tempDir.resolve("missing.properties");

        String output = run(new String[] {config.toString()});

        assertEquals("Error: Cannot read configuration file " + config + NL,
                output);
    }

    @Test
    void overloadedKnightIsReported() throws IOException {
        Path config = writeConfig("Squire", "1",
                "CUIRASS;Milanese Cuirass;12;450;STEEL;60");

        String output = run(new String[] {config.toString()});

        assertEquals("Error: Squire cannot carry Milanese Cuirass:"
                + " 12.00 kg exceeds the limit of 1.00 kg" + NL, output);
    }

    @Test
    void mainUsesStandardStreams() throws IOException {
        Path config = writeConfig("Sir Kay", "5", "DAGGER;Dirk;0.3;10;8");
        InputStream originalIn = System.in;
        PrintStream originalOut = System.out;
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        try {
            System.setIn(input("0"));
            System.setOut(new PrintStream(buffer, true,
                    StandardCharsets.UTF_8));

            Main.main(new String[] {config.toString()});
        } finally {
            System.setIn(originalIn);
            System.setOut(originalOut);
        }

        String output = buffer.toString(StandardCharsets.UTF_8);
        assertTrue(output.startsWith("Knight: Sir Kay (items: 1"));
        assertTrue(output.contains("Farewell!"));
    }

    private Path writeConfig(String name, String maxWeight,
            String... ammunition) throws IOException {
        Files.write(tempDir.resolve("ammo.csv"), List.of(ammunition),
                StandardCharsets.UTF_8);
        Path config = tempDir.resolve("knight.properties");
        Files.write(config, List.of("knight.name=" + name,
                "knight.maxCarryWeight=" + maxWeight,
                "ammunition.file=ammo.csv"), StandardCharsets.UTF_8);
        return config;
    }

    private static String run(String[] args, String... lines) {
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        PrintStream out = new PrintStream(buffer, true, StandardCharsets.UTF_8);

        Main.run(args, input(lines), out);

        return buffer.toString(StandardCharsets.UTF_8);
    }

    private static InputStream input(String... lines) {
        String text = String.join(NL, lines);
        return new ByteArrayInputStream(text.getBytes(StandardCharsets.UTF_8));
    }
}
