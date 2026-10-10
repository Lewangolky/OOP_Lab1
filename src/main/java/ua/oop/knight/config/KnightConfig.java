/*
 * KnightConfig.java
 *
 * Version 1.0
 *
 * 2026-10-10
 */
package ua.oop.knight.config;

import java.io.IOException;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Objects;
import java.util.Properties;

import ua.oop.knight.exception.ConfigurationException;

/**
 * Initialization parameters of the application, read from a properties
 * file:
 * <pre>
 * knight.name=Sir Galahad
 * knight.maxCarryWeight=40
 * ammunition.file=ammunition.csv
 * </pre>
 * A relative path of the ammunition file is resolved against the
 * directory of the properties file.
 */
public final class KnightConfig {

    /** Key of the knight's name. */
    public static final String KNIGHT_NAME_KEY = "knight.name";

    /** Key of the maximum weight the knight can carry, in kilograms. */
    public static final String MAX_CARRY_WEIGHT_KEY = "knight.maxCarryWeight";

    /** Key of the path to the ammunition file. */
    public static final String AMMUNITION_FILE_KEY = "ammunition.file";

    private final String knightName;
    private final double maxCarryWeight;
    private final Path ammunitionFile;

    private KnightConfig(String knightName, double maxCarryWeight,
            Path ammunitionFile) {
        this.knightName = knightName;
        this.maxCarryWeight = maxCarryWeight;
        this.ammunitionFile = ammunitionFile;
    }

    /**
     * Reads the configuration from a properties file.
     *
     * @param configFile path to the properties file
     * @return loaded configuration
     * @throws ConfigurationException if the file cannot be read or a
     *                                parameter is missing or invalid
     */
    public static KnightConfig load(Path configFile) {
        Objects.requireNonNull(configFile, "Config file must not be null");
        Properties properties = new Properties();
        try (Reader reader = Files.newBufferedReader(configFile,
                StandardCharsets.UTF_8)) {
            properties.load(reader);
        } catch (IOException e) {
            throw new ConfigurationException(
                    "Cannot read configuration file " + configFile, e);
        }
        Path baseDirectory = configFile.toAbsolutePath().getParent();
        return fromProperties(properties, baseDirectory);
    }

    /**
     * Creates the configuration from already loaded properties.
     *
     * @param properties    configuration parameters
     * @param baseDirectory directory a relative ammunition file path is
     *                      resolved against
     * @return configuration
     * @throws ConfigurationException if a parameter is missing or invalid
     */
    public static KnightConfig fromProperties(Properties properties,
            Path baseDirectory) {
        Objects.requireNonNull(properties, "Properties must not be null");
        Objects.requireNonNull(baseDirectory,
                "Base directory must not be null");
        String knightName = getRequired(properties, KNIGHT_NAME_KEY);
        double maxCarryWeight = parseWeight(
                getRequired(properties, MAX_CARRY_WEIGHT_KEY));
        Path ammunitionFile = baseDirectory.resolve(
                getRequired(properties, AMMUNITION_FILE_KEY));
        return new KnightConfig(knightName, maxCarryWeight, ammunitionFile);
    }

    /**
     * Returns the knight's name.
     *
     * @return name
     */
    public String getKnightName() {
        return knightName;
    }

    /**
     * Returns the maximum weight the knight can carry.
     *
     * @return weight in kilograms
     */
    public double getMaxCarryWeight() {
        return maxCarryWeight;
    }

    /**
     * Returns the path to the ammunition file.
     *
     * @return ammunition file
     */
    public Path getAmmunitionFile() {
        return ammunitionFile;
    }

    private static String getRequired(Properties properties, String key) {
        String value = properties.getProperty(key);
        if (value == null || value.isBlank()) {
            throw new ConfigurationException(
                    "Missing configuration parameter: " + key);
        }
        return value.trim();
    }

    private static double parseWeight(String value) {
        double weight;
        try {
            weight = Double.parseDouble(value);
        } catch (NumberFormatException e) {
            throw new ConfigurationException(
                    "Invalid " + MAX_CARRY_WEIGHT_KEY + ": " + value, e);
        }
        if (!(weight > 0) || Double.isInfinite(weight)) {
            throw new ConfigurationException(
                    MAX_CARRY_WEIGHT_KEY + " must be a positive number: "
                    + value);
        }
        return weight;
    }
}
