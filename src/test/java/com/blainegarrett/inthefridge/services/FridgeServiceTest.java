package com.blainegarrett.inthefridge.services;

import com.blainegarrett.inthefridge.entities.FridgeEntity;
import com.blainegarrett.inthefridge.entities.FridgeItemEntity;
import com.blainegarrett.inthefridge.entities.ItemEntity;
import com.blainegarrett.inthefridge.exceptions.ItemNotInFridgeException;
import com.blainegarrett.inthefridge.exceptions.TooMuchSodaException;
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

  @Test
  public void testAddItemToFridgeSodaMax() {
    // Set Up Test
    FridgeService service = new FridgeService(repo);
    FridgeEntity f1 = FridgeEntity.create("mockFridge");
    ItemEntity i1 = ItemEntity.create("soda13", "soda");
    Mockito.doReturn(12).when(repo).getCountByType(Matchers.anyString(), Matchers.anyString());

    // Run Code To Test
    assertThrows(TooMuchSodaException.class, () -> service.addItemToFridge(f1, i1));

    // Check Results - If we're still here, Exception WAS Thrown
    Mockito.verify(repo, Mockito.times(1)).getCountByType("mockFridge", "soda");
  }

  @Test
  public void testAddItemToFridgeSodaSuccess() throws Exception {
    // Set Up Test
    FridgeService service = new FridgeService(repo);
    FridgeEntity f1 = FridgeEntity.create("mockFridge");
    ItemEntity i1 = ItemEntity.create("soda1", "soda");
    FridgeItemEntity fi1 = FridgeItemEntity.create("612", f1, i1);

    Mockito.doReturn(0).when(repo).getCountByType(Matchers.anyString(), Matchers.anyString());
    Mockito.doReturn(Optional.of(fi1)).when(repo).addItem(Matchers.anyString(), Matchers.anyString(), Matchers.anyString());

    // Run Code To Test
    Optional<FridgeItemEntity> result = service.addItemToFridge(f1, i1);

    // Check Results
    FridgeItemEntity fiResult = result.get();
    assertEquals("mockFridge", fiResult.getFridge().getId());
    assertEquals("soda1", fiResult.getItem().getId());
    assertEquals("612", fiResult.getId());

    Mockito.verify(repo, Mockito.times(1)).getCountByType("mockFridge", "soda");
    Mockito.verify(repo, Mockito.times(1)).addItem("mockFridge", "soda1", "soda");
  }

  @Test
  public void testRemoveFromFridgeWrongFridge() throws Exception {
    // Set Up Test
    FridgeService service = new FridgeService(repo);
    FridgeEntity f1 = FridgeEntity.create("mockFridge");
    FridgeEntity f2 = FridgeEntity.create("otherFridge");
    ItemEntity i1 = ItemEntity.create("soda1", "soda");
    FridgeItemEntity fi1 = FridgeItemEntity.create("612", f1, i1);
    Mockito.doReturn(true).when(repo).deleteFridgeItem(Matchers.anyString());

    // Run Code To Test
    assertThrows(ItemNotInFridgeException.class, () -> service.removeItemFromFridge(f2, fi1));
    Mockito.verify(repo, Mockito.times(0)).deleteFridgeItem(Matchers.anyString());
  }

  @Test
  public void testRemoveFromFridgeSuccess() throws Exception {
    // Set Up Test
    FridgeService service = new FridgeService(repo);
    FridgeEntity f1 = FridgeEntity.create("mockFridge");
    ItemEntity i1 = ItemEntity.create("soda1", "soda");
    FridgeItemEntity fi1 = FridgeItemEntity.create("612", f1, i1);
    Mockito.doReturn(true).when(repo).deleteFridgeItem(Matchers.anyString());

    // Run Code To Test
    boolean result = service.removeItemFromFridge(f1, fi1);

    // Check Results
    assertEquals(true, result);
    Mockito.verify(repo, Mockito.times(1)).deleteFridgeItem("612");
  }

  @Test void getItemsFromFridge() {
    // Set Up Test
    FridgeService service = new FridgeService(repo);
    FridgeEntity f1 = FridgeEntity.create("mockFridge");
    ItemEntity i1 = ItemEntity.create("coke", "soda");
    FridgeItemEntity fi1 = FridgeItemEntity.create("coke1", f1, i1);
    FridgeItemEntity fi2 = FridgeItemEntity.create("coke2", f1, i1);
    FridgeItemEntity fi3 = FridgeItemEntity.create("coke3", f1, i1);
    List<FridgeItemEntity> threeCokes = List.of(fi1, fi2, fi3);
    Mockito.doReturn(threeCokes).when(repo).getFridgeItems(Matchers.anyString());

    // Run Code To Test
    List<FridgeItemEntity> result = service.getItemsFromFridge(f1);

    // Check Results
    assertEquals(3, result.size());
    assertEquals("coke1", result.get(0).getId());
    assertEquals("coke2", result.get(1).getId());
    assertEquals("coke3", result.get(2).getId());
  }

  @Test void getItemFromFridgeSuccess() throws Exception {
    // Set Up Test
    FridgeService service = new FridgeService(repo);
    FridgeEntity f1 = FridgeEntity.create("mockFridge");
    ItemEntity i1 = ItemEntity.create("coke", "soda");
    FridgeItemEntity fi1 = FridgeItemEntity.create("coke1", f1, i1);
    Mockito.doReturn(Optional.of(fi1)).when(repo).getFridgeItem(Matchers.anyString());

    // Run Code To Test
    Optional<FridgeItemEntity> result = service.getItemFromFridge(f1, "coke1");

    // Check Results
    Mockito.verify(repo, Mockito.times(1)).getFridgeItem("coke1");
    assertEquals("coke1", result.get().getId());
  }

  @Test void getItemFromFridgeWrongFridge() throws Exception {
    // Set Up Test
    FridgeService service = new FridgeService(repo);
    FridgeEntity f1 = FridgeEntity.create("mockFridge");
    FridgeEntity f2 = FridgeEntity.create("otherFridge");
    ItemEntity i1 = ItemEntity.create("soda1", "soda");
    FridgeItemEntity fi1 = FridgeItemEntity.create("coke1", f1, i1);
    Mockito.doReturn(Optional.of(fi1)).when(repo).getFridgeItem(Matchers.anyString());

    // Run Code To Test
    assertThrows(ItemNotInFridgeException.class, () -> service.getItemFromFridge(f2, fi1.getId()));
    Mockito.verify(repo, Mockito.times(1)).getFridgeItem("coke1");
  }
}




