package com.blainegarrett.inthefridge.entities;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ItemEntityTest {
  @Test
  public void testCreate() {
    ItemEntity i1 = ItemEntity.create("aSoda", "soda");
    assertEquals("aSoda", i1.getId());
    assertEquals("soda", i1.getType());
  }
}
