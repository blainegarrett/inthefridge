package com.blainegarrett.inthefridge.entities;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class FridgeEntityTest {
  @Test
  public void testCreate() {
    FridgeEntity f1 = FridgeEntity.create("myFridge");
    assertEquals("myFridge", f1.getId());
  }
}
