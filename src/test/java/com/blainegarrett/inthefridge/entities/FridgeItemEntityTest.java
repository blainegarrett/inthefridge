package com.blainegarrett.inthefridge.entities;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class FridgeItemEntityTest {
  @Test
  public void testCreate() {
    // Set Up Test
    ItemEntity i1 = ItemEntity.create("aSoda", "soda");
    FridgeEntity f1 = FridgeEntity.create("myFridge");

    // Run Code to Test
    FridgeItemEntity fi1 = FridgeItemEntity.create("sodaInMyFridge", f1, i1);

    // Check Results
    assertEquals("sodaInMyFridge", fi1.getId());
    assertEquals("myFridge", fi1.getFridge().getId());
    assertEquals("aSoda", fi1.getItem().getId());
  }
}
