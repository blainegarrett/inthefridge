package com.blainegarrett.inthefridge;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ApplicationTest {
  @Test
  public void rootHandler() {
    var app = new Application();
    var result = app.rootHandler();
    assertEquals("What is in the fridge? See https://github.com/blainegarrett/inthefridge to find out.", result);
  }

  @Test
  public void testLucky() {
    var app = new Application();

    var result = app.getFridgeHandler("asdf");
    assertEquals("TODO: Show fridge with ID: asdf", result);
  }
}