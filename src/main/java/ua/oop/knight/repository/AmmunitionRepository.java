/*
 * AmmunitionRepository.java
 *
 * Version 1.0
 *
 * 2026-10-10
 */
package ua.oop.knight.repository;

import java.util.List;

import ua.oop.knight.model.ammunition.Ammunition;

/**
 * Source of the ammunition available for a knight.
 */
public interface AmmunitionRepository {

    /**
     * Returns all the available ammunition.
     *
     * @return list of items
     * @throws ua.oop.knight.exception.ConfigurationException if the items
     *         cannot be loaded
     */
    List<Ammunition> findAll();
}
