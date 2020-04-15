package com.blainegarrett.inthefridge.services;

import org.junit.jupiter.api.Test;

import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class FridgeServiceTest {
  @Test
  public void testGetAllFridges() {
    // Set Up Test
    var service = new FridgeService();

    // Run Code To Test
    var result = service.getFridges();

    // Check Results
    assertEquals(2, result.size());

    // TODO: Verify items are as expected
  }

  @Test
  public void testGetByIdThrows() {
    // Set Up Test
    var service = new FridgeService();

    // Run Code To Test
    var  result = service.getById("zachFridge");

    // Check Results
    // Zach DOES NOT have a fridge
    assertThrows(NoSuchElementException.class, () -> result.get());
  }

  @Test
  public void testGetByIdSuccess() {
    // Set Up Test
    var service = new FridgeService();

    // Run Code To Test
    var  result = service.getById("blaineFridge");

    // Check Results
    // Blaine DOES have a Fridge
    assertEquals("blaineFridge", result.get().getId());
  }
}




