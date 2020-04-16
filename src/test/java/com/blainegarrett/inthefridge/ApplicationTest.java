package com.blainegarrett.inthefridge;

import org.junit.jupiter.api.Test;
import org.springframework.web.server.ResponseStatusException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class ApplicationTest {
  @Test
  public void rootHandler() {
    Application app = new Application();
    String result = app.rootHandler();
    assertEquals("What is in the fridge? See https://github.com/blainegarrett/inthefridge to find out.", result);
  }

  @Test
  public void testGetItemsHandlerEmpty() {
    // Test to ensure that handler correctly returns empty list when no data
    Application app = new Application();
    assertEquals(0, app.getItemsHandler().size());
  }

  @Test
  public void testGetFridgesHandlerEmpty() {
    // Test to ensure that handler correctly returns empty list when no data
    Application app = new Application();
    assertEquals(0, app.getFridgesHandler().size());
  }

  @Test
  public void testGetFridgeHandler404() {
    // Test to ensure that handler throws 404 when fridge not found
    Application app = new Application();
    assertThrows(ResponseStatusException.class, () -> app.getFridgeHandler("404-fridge"));
  }

  @Test
  public void testGetFridgeHandlerFridge404() {
    // Test to ensure that handler throws 404 when fridge not found
    Application app = new Application();
    assertThrows(ResponseStatusException.class, () -> app.getFridgeHandler("404-fridge"));
  }


  @Test
  public void testGetFridgeContentsHandlerFridge404() {
    // Test to ensure that handler throws 404 when fridge not found
    Application app = new Application();
    assertThrows(ResponseStatusException.class, () -> app.getFridgeContentsHandler("404-fridge"));
  }

  @Test
  public void testAddItemToFridgeHandlerFridge404() {
    // Test to ensure that handler throws 404 when fridge not found
    Application app = new Application();
    assertThrows(ResponseStatusException.class, () -> app.addItemToFridgeHandler("404-fridge", "coke"));
  }

  @Test
  public void testGetItemToFridgeHandlerFridge404() {
    // Test to ensure that handler throws 404 when fridge not found
    Application app = new Application();
    assertThrows(ResponseStatusException.class, () -> app.getItemFromFridgeHandler("404-fridge", "mycoke"));
  }

  @Test
  public void testDeleteItemFromFridgeHandlerFridge404() {
    // Test to ensure that handler throws 404 when fridge not found
    Application app = new Application();
    assertThrows(ResponseStatusException.class, () -> app.deleteItemFromFridgeHandler("404-fridge", "mycoke"));
  }
}