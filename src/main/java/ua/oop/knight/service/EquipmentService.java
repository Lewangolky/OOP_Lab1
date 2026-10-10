/*
 * EquipmentService.java
 *
 * Version 1.0
 *
 * 2026-10-10
 */
package ua.oop.knight.service;

import java.util.Objects;

import ua.oop.knight.model.Knight;
import ua.oop.knight.model.ammunition.Ammunition;
import ua.oop.knight.repository.AmmunitionRepository;

/**
 * Equips knights with the ammunition taken from a repository.
 */
public class EquipmentService {

    private final AmmunitionRepository repository;

    /**
     * Creates a service that takes ammunition from the given repository.
     *
     * @param repository source of the ammunition
     */
    public EquipmentService(AmmunitionRepository repository) {
        this.repository = Objects.requireNonNull(repository,
                "Repository must not be null");
    }

    /**
     * Puts every item from the repository on the knight in the order the
     * repository returns them. A later item replaces an earlier one
     * meant for the same slot.
     *
     * @param knight knight to equip
     * @throws ua.oop.knight.exception.OverloadException if the knight
     *         cannot carry the ammunition
     */
    public void equip(Knight knight) {
        Objects.requireNonNull(knight, "Knight must not be null");
        for (Ammunition item : repository.findAll()) {
            knight.equip(item);
        }
    }
}
