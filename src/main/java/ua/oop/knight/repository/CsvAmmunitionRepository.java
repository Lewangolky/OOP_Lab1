/*
 * CsvAmmunitionRepository.java
 *
 * Version 1.0
 *
 * 2026-10-10
 */
package ua.oop.knight.repository;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import ua.oop.knight.exception.ConfigurationException;
import ua.oop.knight.model.ammunition.Ammunition;

/**
 * Repository that reads ammunition from a text file, one record per
 * line. Blank lines and lines starting with {@value #COMMENT_PREFIX}
 * are skipped.
 *
 * @see AmmunitionParser
 */
public class CsvAmmunitionRepository implements AmmunitionRepository {

    /** Prefix of a comment line. */
    public static final String COMMENT_PREFIX = "#";

    /** Byte order mark some editors put at the start of a UTF-8 file. */
    private static final String BYTE_ORDER_MARK = "﻿";

    private final Path file;
    private final AmmunitionParser parser;

    /**
     * Creates a repository backed by the given file.
     *
     * @param file   file with ammunition records
     * @param parser converter of a record into an item
     */
    public CsvAmmunitionRepository(Path file, AmmunitionParser parser) {
        this.file = Objects.requireNonNull(file, "File must not be null");
        this.parser = Objects.requireNonNull(parser,
                "Parser must not be null");
    }

    /**
     * Reads all the ammunition from the file.
     *
     * @return items in the order they appear in the file
     * @throws ConfigurationException if the file cannot be read or
     *                                contains an invalid record
     */
    @Override
    public List<Ammunition> findAll() {
        List<String> lines = readLines();
        List<Ammunition> items = new ArrayList<>();
        for (int i = 0; i < lines.size(); i++) {
            String line = lines.get(i).replace(BYTE_ORDER_MARK, "").trim();
            if (line.isEmpty() || line.startsWith(COMMENT_PREFIX)) {
                continue;
            }
            try {
                items.add(parser.parse(line));
            } catch (IllegalArgumentException e) {
                throw new ConfigurationException(String.format(
                        "Invalid record at %s:%d: %s",
                        file, i + 1, e.getMessage()), e);
            }
        }
        return items;
    }

    private List<String> readLines() {
        try {
            return Files.readAllLines(file, StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new ConfigurationException(
                    "Cannot read ammunition file " + file, e);
        }
    }
}
