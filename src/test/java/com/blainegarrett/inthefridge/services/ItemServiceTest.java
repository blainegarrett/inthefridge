package com.blainegarrett.inthefridge.services;

import org.junit.jupiter.api.Test;

import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class ItemServiceTest {
  @Test
  public void testGetAllItems() {
    // Set Up Test
    var service = new ItemService();

    // Run Code To Test
    var result = service.getItems();

    // Check Results
    assertEquals(2, result.size());

    // TODO: Verify items are as expected
  }

  @Test
  public void testGetByIdThrows() {
    // Set Up Test
    var service = new ItemService();

    // Run Code To Test
    var  result = service.getById("nails");

    // Check Results
    // Nails DO NOT go in the fridge...
    assertThrows(NoSuchElementException.class, () -> result.get());
  }

  @Test
  public void testGetByIdSuccess() {
    // Set Up Test
    var service = new ItemService();

    // Run Code To Test
    var  result = service.getById("milk");

    // Check Results
    // Milk DOES belong in a fridge
    assertEquals("milk", result.get().getId());
  }
}




