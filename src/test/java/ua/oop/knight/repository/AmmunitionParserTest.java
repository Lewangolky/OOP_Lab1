/*
 * AmmunitionParserTest.java
 *
 * Version 1.0
 *
 * 2026-10-10
 */
package ua.oop.knight.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.math.BigDecimal;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import ua.oop.knight.model.ammunition.Ammunition;
import ua.oop.knight.model.ammunition.armor.Cuirass;
import ua.oop.knight.model.ammunition.armor.Gauntlets;
import ua.oop.knight.model.ammunition.armor.Greaves;
import ua.oop.knight.model.ammunition.armor.Helmet;
import ua.oop.knight.model.ammunition.armor.Material;
import ua.oop.knight.model.ammunition.armor.Shield;
import ua.oop.knight.model.ammunition.weapon.Dagger;
import ua.oop.knight.model.ammunition.weapon.Mace;
import ua.oop.knight.model.ammunition.weapon.Sword;

class AmmunitionParserTest {

    private final AmmunitionParser parser = new AmmunitionParser();

    @Test
    void parsesHelmet() {
        Helmet helmet = assertInstanceOf(Helmet.class,
                parser.parse("HELMET;Great Helm;2.5;120.00;STEEL;35;true"));

        assertEquals("Great Helm", helmet.getName());
        assertEquals(2.5, helmet.getWeight());
        assertEquals(new BigDecimal("120.00"), helmet.getPrice());
        assertEquals(Material.STEEL, helmet.getMaterial());
        assertEquals(35, helmet.getProtection());
        assertTrue(helmet.hasVisor());
    }

    @Test
    void parsesCuirass() {
        Cuirass cuirass = assertInstanceOf(Cuirass.class,
                parser.parse("CUIRASS;Brigandine;9;300;LEATHER;40"));

        assertEquals(Material.LEATHER, cuirass.getMaterial());
        assertEquals(40, cuirass.getProtection());
    }

    @Test
    void parsesGauntlets() {
        Gauntlets gauntlets = assertInstanceOf(Gauntlets.class,
                parser.parse("GAUNTLETS;Mail Mittens;1;60;CHAIN_MAIL;8"));

        assertEquals(Material.CHAIN_MAIL, gauntlets.getMaterial());
    }

    @Test
    void parsesGreaves() {
        Greaves greaves = assertInstanceOf(Greaves.class,
                parser.parse("GREAVES;Plate Greaves;4.2;150;IRON;25"));

        assertEquals(25, greaves.getProtection());
    }

    @Test
    void parsesShield() {
        Shield shield = assertInstanceOf(Shield.class,
                parser.parse("SHIELD;Heater Shield;4.5;95;IRON;30;25"));

        assertEquals(30, shield.getProtection());
        assertEquals(25, shield.getBlockChance());
    }

    @Test
    void parsesSword() {
        Sword sword = assertInstanceOf(Sword.class,
                parser.parse("SWORD;Longsword;1.6;300;45;95"));

        assertEquals(45, sword.getDamage());
        assertEquals(95, sword.getBladeLength());
    }

    @Test
    void parsesMace() {
        Mace mace = assertInstanceOf(Mace.class,
                parser.parse("MACE;Flanged Mace;2.3;180;40;false"));

        assertEquals(40, mace.getDamage());
        assertFalse(mace.isSpiked());
    }

    @Test
    void parsesDagger() {
        Dagger dagger = assertInstanceOf(Dagger.class,
                parser.parse("DAGGER;Rondel Dagger;0.4;40;15"));

        assertEquals(15, dagger.getDamage());
    }

    @Test
    void ignoresCaseOfKeywordsAndSpacesAroundFields() {
        Ammunition item = parser.parse(
                " helmet ; Sallet ; 2 ; 90 ; steel ; 30 ; FALSE ");

        Helmet helmet = assertInstanceOf(Helmet.class, item);
        assertEquals("Sallet", helmet.getName());
        assertEquals(Material.STEEL, helmet.getMaterial());
        assertFalse(helmet.hasVisor());
    }

    @ParameterizedTest
    @CsvSource(delimiter = '|', value = {
        "BOW;Longbow;1;50;30                   | Unknown ammunition type: BOW",
        "DAGGER;Rondel Dagger;0.4;40           | DAGGER record must have 5"
                + " fields, but has 4",
        "HELMET;Helm;2;90;STEEL;30;true;extra  | HELMET record must have 7"
                + " fields, but has 8",
        "DAGGER;Rondel Dagger;light;40;15      | Invalid weight: 'light'",
        "DAGGER;Rondel Dagger;0.4;cheap;15     | Invalid price: 'cheap'",
        "DAGGER;Rondel Dagger;0.4;40;sharp     | Invalid damage: 'sharp'",
        "CUIRASS;Cuirass;9;300;GOLD;40         | Unknown material: GOLD",
        "CUIRASS;Cuirass;9;300;STEEL;high      | Invalid protection: 'high'",
        "HELMET;Helm;2;90;STEEL;30;yes         | Invalid visor: 'yes'",
        "SHIELD;Buckler;1;20;IRON;5;often      | Invalid block chance:"
                + " 'often'",
        "SWORD;Arming Sword;1.2;200;40;long    | Invalid blade length:"
                + " 'long'",
        "MACE;Mace;2;100;40;maybe              | Invalid spiked: 'maybe'",
        "DAGGER;Rondel Dagger;-0.4;40;15       | Weight must be a positive"
                + " number: -0.4",
        "DAGGER; ;0.4;40;15                    | Name must not be blank"
    })
    void rejectsMalformedRecord(String record, String expectedMessage) {
        IllegalArgumentException e = assertThrows(
                IllegalArgumentException.class, () -> parser.parse(record));

        assertEquals(expectedMessage, e.getMessage());
    }

    @Test
    void rejectsNullRecord() {
        assertThrows(NullPointerException.class, () -> parser.parse(null));
    }
}
