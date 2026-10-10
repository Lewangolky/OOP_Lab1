/*
 * EquipmentServiceTest.java
 *
 * Version 1.0
 *
 * 2026-10-10
 */
package ua.oop.knight.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InOrder;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import ua.oop.knight.exception.ConfigurationException;
import ua.oop.knight.exception.OverloadException;
import ua.oop.knight.model.Knight;
import ua.oop.knight.model.ammunition.Ammunition;
import ua.oop.knight.repository.AmmunitionRepository;
import ua.oop.knight.testutil.AmmunitionFixtures;

@ExtendWith(MockitoExtension.class)
class EquipmentServiceTest {

    @Mock
    private AmmunitionRepository repository;

    @Mock
    private Knight knight;

    @InjectMocks
    private EquipmentService service;

    @Test
    void equipPutsEveryItemOnKnightInRepositoryOrder() {
        Ammunition helmet = AmmunitionFixtures.helmet();
        Ammunition sword = AmmunitionFixtures.sword();
        when(repository.findAll()).thenReturn(List.of(helmet, sword));

        service.equip(knight);

        InOrder order = inOrder(knight);
        order.verify(knight).equip(helmet);
        order.verify(knight).equip(sword);
        verifyNoMoreInteractions(knight);
    }

    @Test
    void emptyRepositoryLeavesKnightUntouched() {
        when(repository.findAll()).thenReturn(List.of());

        service.equip(knight);

        verifyNoInteractions(knight);
    }

    @Test
    void overloadIsPropagated() {
        when(repository.findAll()).thenReturn(
                List.of(AmmunitionFixtures.cuirass()));
        when(knight.equip(any())).thenThrow(new OverloadException("heavy"));

        assertThrows(OverloadException.class, () -> service.equip(knight));
    }

    @Test
    void repositoryFailureIsPropagated() {
        when(repository.findAll()).thenThrow(
                new ConfigurationException("broken file"));

        assertThrows(ConfigurationException.class,
                () -> service.equip(knight));
        verifyNoInteractions(knight);
    }

    @Test
    void realKnightReceivesWholeSet() {
        when(repository.findAll()).thenReturn(AmmunitionFixtures.fullSet());
        Knight galahad = new Knight("Sir Galahad", 40);

        service.equip(galahad);

        assertEquals(AmmunitionFixtures.fullSet().size(),
                galahad.getAmmunition().size());
        assertEquals(AmmunitionFixtures.FULL_SET_WEIGHT,
                galahad.getTotalWeight(), 1e-9);
    }

    @Test
    void nullArgumentsAreRejected() {
        assertThrows(NullPointerException.class,
                () -> new EquipmentService(null));
        assertThrows(NullPointerException.class, () -> service.equip(null));
    }
}
