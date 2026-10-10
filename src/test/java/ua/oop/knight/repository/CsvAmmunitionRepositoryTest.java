/*
 * CsvAmmunitionRepositoryTest.java
 *
 * Version 1.0
 *
 * 2026-10-10
 */
package ua.oop.knight.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import org.mockito.InOrder;

import ua.oop.knight.exception.ConfigurationException;
import ua.oop.knight.model.ammunition.Ammunition;
import ua.oop.knight.model.ammunition.armor.Helmet;
import ua.oop.knight.model.ammunition.weapon.Dagger;

class CsvAmmunitionRepositoryTest {

    @TempDir
    Path tempDir;

    @Test
    void findAllParsesEveryRecordInFileOrder() throws IOException {
        Path file = write("HELMET;Great Helm;2.5;120;STEEL;35;true",
                "DAGGER;Rondel Dagger;0.4;40;15");
        AmmunitionRepository repository = new CsvAmmunitionRepository(file,
                new AmmunitionParser());

        List<Ammunition> items = repository.findAll();

        assertEquals(2, items.size());
        assertInstanceOf(Helmet.class, items.get(0));
        assertInstanceOf(Dagger.class, items.get(1));
    }

    @Test
    void findAllSkipsCommentsAndBlankLines() throws IOException {
        Path file = write("﻿# ammunition",
                "",
                "  HELMET;Great Helm;2.5;120;STEEL;35;true  ",
                "   ",
                "  # DAGGER;Rondel Dagger;0.4;40;15",
                "SWORD;Longsword;1.6;300;45;95");
        AmmunitionParser parser = mock(AmmunitionParser.class);
        Ammunition helmet = mock(Ammunition.class);
        Ammunition sword = mock(Ammunition.class);
        when(parser.parse("HELMET;Great Helm;2.5;120;STEEL;35;true"))
                .thenReturn(helmet);
        when(parser.parse("SWORD;Longsword;1.6;300;45;95")).thenReturn(sword);

        List<Ammunition> items = new CsvAmmunitionRepository(file, parser)
                .findAll();

        assertEquals(List.of(helmet, sword), items);
        InOrder order = inOrder(parser);
        order.verify(parser).parse("HELMET;Great Helm;2.5;120;STEEL;35;true");
        order.verify(parser).parse("SWORD;Longsword;1.6;300;45;95");
        verifyNoMoreInteractions(parser);
    }

    @Test
    void findAllReturnsEmptyListForEmptyFile() throws IOException {
        Path file = write();

        List<Ammunition> items = new CsvAmmunitionRepository(file,
                new AmmunitionParser()).findAll();

        assertTrue(items.isEmpty());
    }

    @Test
    void invalidRecordIsReportedWithLineNumber() throws IOException {
        Path file = write("# header", "DAGGER;Rondel Dagger;0.4;40;15",
                "DAGGER;Broken Dagger;heavy;40;15");
        AmmunitionRepository repository = new CsvAmmunitionRepository(file,
                new AmmunitionParser());

        ConfigurationException e = assertThrows(ConfigurationException.class,
                repository::findAll);

        assertEquals("Invalid record at " + file + ":3: Invalid weight:"
                + " 'heavy'", e.getMessage());
        assertInstanceOf(IllegalArgumentException.class, e.getCause());
    }

    @Test
    void missingFileIsReported() {
        Path file = tempDir.resolve("missing.csv");
        AmmunitionRepository repository = new CsvAmmunitionRepository(file,
                new AmmunitionParser());

        ConfigurationException e = assertThrows(ConfigurationException.class,
                repository::findAll);

        assertEquals("Cannot read ammunition file " + file, e.getMessage());
        assertInstanceOf(IOException.class, e.getCause());
    }

    @Test
    void shippedAmmunitionFileIsValid() {
        Path file = Paths.get("config", "ammunition.csv");

        List<Ammunition> items = new CsvAmmunitionRepository(file,
                new AmmunitionParser()).findAll();

        assertEquals(7, items.size());
    }

    @Test
    void nullArgumentsAreRejected() {
        AmmunitionParser parser = new AmmunitionParser();

        assertThrows(NullPointerException.class,
                () -> new CsvAmmunitionRepository(null, parser));
        assertThrows(NullPointerException.class,
                () -> new CsvAmmunitionRepository(tempDir, null));
    }

    private Path write(String... lines) throws IOException {
        Path file = tempDir.resolve("ammunition.csv");
        Files.write(file, List.of(lines), StandardCharsets.UTF_8);
        return file;
    }
}
