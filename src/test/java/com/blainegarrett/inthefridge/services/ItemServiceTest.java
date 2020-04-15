package com.blainegarrett.inthefridge.services;

import com.blainegarrett.inthefridge.entities.ItemEntity;
import com.blainegarrett.inthefridge.repositories.ItemRepositoryMemory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Matchers;
import org.mockito.Mockito;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class ItemServiceTest {
  private ItemRepositoryMemory repo = Mockito.mock(ItemRepositoryMemory.class);;

  @BeforeEach
  public void setUp () {
    // Reset Mocks if needed...
  }

  @Test
  public void testGetAllItems() {
    // Set Up Test
    ItemService service = new ItemService(repo);

    ItemEntity i1 = ItemEntity.create("izzygrape", "soda");
    ItemEntity i2 = ItemEntity.create("milk", "dairy");
    List<ItemEntity> mockReturnList = List.of(i1, i2);
    Mockito.doReturn(mockReturnList).when(repo).query();

    // Run Code To Test
    List<ItemEntity> result = service.getItems();

    // Check Results
    assertEquals(2, result.size());
    assertEquals("izzygrape", result.get(0).getId());
    assertEquals("milk", result.get(1).getId());

  }

  @Test
  public void testGetByIdThrows() {
    // Set Up Test
    ItemService service = new ItemService(repo);
    Mockito.doReturn(Optional.empty()).when(repo).getById(Matchers.anyString());

    // Run Code To Test
    Optional<ItemEntity> result = service.getById("nails");

    // Check Results
    // Nails DO NOT go in the fridge...
    Mockito.verify(repo, Mockito.times(1)).getById("nails");
    assertThrows(NoSuchElementException.class, () -> result.get());
  }

  @Test
  public void testGetByIdSuccess() {
    // Set Up Test
    ItemService service = new ItemService(repo);
    ItemEntity f1 = ItemEntity.create("mockMilk", "dairy");
    Mockito.doReturn(Optional.of(f1)).when(repo).getById("milk");

    // Run Code To Test
    Optional<ItemEntity> result = service.getById("milk");

    // Check Results
    // Milk DOES belong in a fridge
    assertEquals("mockMilk", result.get().getId());
    Mockito.verify(repo, Mockito.times(1)).getById("milk");
  }
}




