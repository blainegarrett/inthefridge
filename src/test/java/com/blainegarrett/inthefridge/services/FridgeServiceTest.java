package com.blainegarrett.inthefridge.services;

import com.blainegarrett.inthefridge.entities.FridgeEntity;
import com.blainegarrett.inthefridge.repositories.FridgeRepositoryMemory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Matchers;
import org.mockito.Mockito;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;


public class FridgeServiceTest {
  private FridgeRepositoryMemory repo = Mockito.mock(FridgeRepositoryMemory.class);;

  @BeforeEach
  public void setUp () {
    // Reset Mocks if needed...
  }

  @Test
  public void testGetAllFridges() {
    // Set Up Test
    FridgeService service = new FridgeService(repo);

    FridgeEntity f1 = FridgeEntity.create("mockFridge1");
    FridgeEntity f2 = FridgeEntity.create("mockFridge2");
    List<FridgeEntity> mockReturnList = List.of(f1, f2);
    Mockito.doReturn(mockReturnList).when(repo).query();

    // Run Code To Test
    List<FridgeEntity> result = service.getFridges();

    // Check Results
    assertEquals(2, result.size());
    assertEquals("mockFridge1", result.get(0).getId());
    assertEquals("mockFridge2", result.get(1).getId());
  }

  @Test
  public void testGetByIdThrows() {
    // Set Up Test
    FridgeService service = new FridgeService(repo);
    Mockito.doReturn(Optional.empty()).when(repo).getById(Matchers.anyString());

    // Run Code To Test
    Optional<FridgeEntity> result = service.getById("zachFridge");

    // Check Results
    // Zach DOES NOT have a fridge
    Mockito.verify(repo, Mockito.times(1)).getById("zachFridge");
    assertThrows(NoSuchElementException.class, () -> result.get());
  }

  @Test
  public void testGetByIdSuccess() {
    // Set Up Test
    FridgeService service = new FridgeService(repo);
    FridgeEntity f1 = FridgeEntity.create("mockFridge");
    Mockito.doReturn(Optional.of(f1)).when(repo).getById("blaineFridge");

    // Run Code To Test
    Optional<FridgeEntity> result = service.getById("blaineFridge");

    // Check Results
    // Blaine DOES have a Fridge
    assertEquals("mockFridge", result.get().getId());
    Mockito.verify(repo, Mockito.times(1)).getById("blaineFridge");
  }
}




