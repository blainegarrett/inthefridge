package com.blainegarrett.inthefridge;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ApplicationTest {
  @Test
  public void rootHandler() {
    Application app = new Application();
    String result = app.rootHandler();
    assertEquals("What is in the fridge? See https://github.com/blainegarrett/inthefridge to find out.", result);
  }

  @Test
  public void testGetFridgeHandler() {
    Application app = new Application();

    String result = app.getFridgeHandler("asdf");
    assertEquals("TODO: Show fridge with ID: asdf", result);
  }
}