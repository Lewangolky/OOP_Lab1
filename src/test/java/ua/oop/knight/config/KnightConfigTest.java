/*
 * KnightConfigTest.java
 *
 * Version 1.0
 *
 * 2026-10-10
 */
package ua.oop.knight.config;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Properties;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import ua.oop.knight.exception.ConfigurationException;

class KnightConfigTest {

    @TempDir
    Path tempDir;

    private Properties properties;

    @BeforeEach
    void setUp() {
        properties = new Properties();
        properties.setProperty(KnightConfig.KNIGHT_NAME_KEY, "Sir Galahad");
        properties.setProperty(KnightConfig.MAX_CARRY_WEIGHT_KEY, "40.5");
        properties.setProperty(KnightConfig.AMMUNITION_FILE_KEY, "ammo.csv");
    }

    @Test
    void loadReadsParametersFromFile() throws IOException {
        Path configFile = tempDir.resolve("knight.properties");
        Files.write(configFile, List.of("# knight",
                "knight.name = Sir Percival ",
                "knight.maxCarryWeight=35",
                "ammunition.file=data/ammo.csv"), StandardCharsets.UTF_8);

        KnightConfig config = KnightConfig.load(configFile);

        assertEquals("Sir Percival", config.getKnightName());
        assertEquals(35, config.getMaxCarryWeight());
        assertEquals(tempDir.toAbsolutePath().resolve("data/ammo.csv"),
                config.getAmmunitionFile());
    }

    @Test
    void loadReportsMissingFile() {
        Path configFile = tempDir.resolve("missing.properties");

        ConfigurationException e = assertThrows(ConfigurationException.class,
                () -> KnightConfig.load(configFile));

        assertEquals("Cannot read configuration file " + configFile,
                e.getMessage());
        assertInstanceOf(IOException.class, e.getCause());
    }

    @Test
    void shippedConfigurationIsValid() {
        KnightConfig config = KnightConfig.load(
                Paths.get("config", "knight.properties"));

        assertEquals("Sir Galahad", config.getKnightName());
        assertEquals(40, config.getMaxCarryWeight());
        assertTrue(Files.isRegularFile(config.getAmmunitionFile()));
    }

    @Test
    void relativeAmmunitionFileIsResolvedAgainstBaseDirectory() {
        KnightConfig config = KnightConfig.fromProperties(properties, tempDir);

        assertEquals("Sir Galahad", config.getKnightName());
        assertEquals(40.5, config.getMaxCarryWeight());
        assertEquals(tempDir.resolve("ammo.csv"), config.getAmmunitionFile());
    }

    @Test
    void absoluteAmmunitionFileIsKept() {
        Path absolute = tempDir.resolve("elsewhere/ammo.csv").toAbsolutePath();
        properties.setProperty(KnightConfig.AMMUNITION_FILE_KEY,
                absolute.toString());

        KnightConfig config = KnightConfig.fromProperties(properties,
                Paths.get("ignored"));

        assertEquals(absolute, config.getAmmunitionFile());
    }

    @ParameterizedTest
    @ValueSource(strings = {KnightConfig.KNIGHT_NAME_KEY,
            KnightConfig.MAX_CARRY_WEIGHT_KEY,
            KnightConfig.AMMUNITION_FILE_KEY})
    void missingParameterIsReported(String key) {
        properties.remove(key);

        ConfigurationException e = assertThrows(ConfigurationException.class,
                () -> KnightConfig.fromProperties(properties, tempDir));

        assertEquals("Missing configuration parameter: " + key,
                e.getMessage());
    }

    @Test
    void blankParameterIsReportedAsMissing() {
        properties.setProperty(KnightConfig.KNIGHT_NAME_KEY, "   ");

        assertThrows(ConfigurationException.class,
                () -> KnightConfig.fromProperties(properties, tempDir));
    }

    @Test
    void nonNumericWeightIsReported() {
        properties.setProperty(KnightConfig.MAX_CARRY_WEIGHT_KEY, "heavy");

        ConfigurationException e = assertThrows(ConfigurationException.class,
                () -> KnightConfig.fromProperties(properties, tempDir));

        assertEquals("Invalid knight.maxCarryWeight: heavy", e.getMessage());
        assertInstanceOf(NumberFormatException.class, e.getCause());
    }

    @ParameterizedTest
    @ValueSource(strings = {"0", "-10", "NaN", "Infinity"})
    void nonPositiveWeightIsReported(String weight) {
        properties.setProperty(KnightConfig.MAX_CARRY_WEIGHT_KEY, weight);

        ConfigurationException e = assertThrows(ConfigurationException.class,
                () -> KnightConfig.fromProperties(properties, tempDir));

        assertEquals("knight.maxCarryWeight must be a positive number: "
                + weight, e.getMessage());
    }

    @Test
    void nullArgumentsAreRejected() {
        assertThrows(NullPointerException.class,
                () -> KnightConfig.load(null));
        assertThrows(NullPointerException.class,
                () -> KnightConfig.fromProperties(null, tempDir));
        assertThrows(NullPointerException.class,
                () -> KnightConfig.fromProperties(properties, null));
    }
}
