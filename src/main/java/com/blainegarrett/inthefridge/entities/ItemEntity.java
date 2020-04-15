package com.blainegarrett.inthefridge.entities;

public class ItemEntity {
  private String id;
  private String itemType;

  public static ItemEntity create(String id, String itemType) {
    return new ItemEntity(id, itemType);
  }

  private ItemEntity(String id, String itemType) {
    this.id = id;
    this.itemType = itemType;
  }

  public String getId() {
    return this.id;
  }
  public String getType() {
    return this.itemType;
  }
}
