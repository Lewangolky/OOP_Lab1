/*
 * AmmunitionService.java
 *
 * Version 1.0
 *
 * 2026-10-10
 */
package ua.oop.knight.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import ua.oop.knight.model.ammunition.Ammunition;

/**
 * Calculations over a collection of ammunition: total cost, sorting
 * by weight and search by price range.
 */
public class AmmunitionService {

    /** Orders items from the lightest to the heaviest, then by name. */
    public static final Comparator<Ammunition> BY_WEIGHT =
            Comparator.comparingDouble(Ammunition::getWeight)
                    .thenComparing(Ammunition::getName);

    /**
     * Calculates the total price of the items.
     *
     * @param items items to price
     * @return sum of the prices, zero for an empty collection
     */
    public BigDecimal calculateTotalCost(Collection<Ammunition> items) {
        Objects.requireNonNull(items, "Items must not be null");
        return items.stream()
                .map(Ammunition::getPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    /**
     * Sorts the items from the lightest to the heaviest. Items of equal
     * weight are ordered by name. The source collection is not changed.
     *
     * @param items items to sort
     * @return new sorted list
     */
    public List<Ammunition> sortByWeight(Collection<Ammunition> items) {
        Objects.requireNonNull(items, "Items must not be null");
        List<Ammunition> sorted = new ArrayList<>(items);
        sorted.sort(BY_WEIGHT);
        return sorted;
    }

    /**
     * Finds the items whose price lies within the given range, bounds
     * included.
     *
     * @param items    items to search
     * @param minPrice lower bound of the price
     * @param maxPrice upper bound of the price
     * @return new list of matching items in the source order
     * @throws IllegalArgumentException if the lower bound exceeds the
     *                                  upper one
     */
    public List<Ammunition> findByPriceRange(Collection<Ammunition> items,
            BigDecimal minPrice, BigDecimal maxPrice) {
        Objects.requireNonNull(items, "Items must not be null");
        Objects.requireNonNull(minPrice, "Min price must not be null");
        Objects.requireNonNull(maxPrice, "Max price must not be null");
        if (minPrice.compareTo(maxPrice) > 0) {
            throw new IllegalArgumentException(
                    "Min price " + minPrice + " exceeds max price "
                    + maxPrice);
        }
        return items.stream()
                .filter(item -> item.getPrice().compareTo(minPrice) >= 0)
                .filter(item -> item.getPrice().compareTo(maxPrice) <= 0)
                .collect(Collectors.toList());
    }
}
