package com.blainegarrett.inthefridge.entities;

public class FridgeEntity {
  private String id;

  public static FridgeEntity create(String id) {
    return new FridgeEntity(id);
  }

  private FridgeEntity(String id) {
    this.id = id;
  }

  public String getId() {
    return this.id;
  }
}
