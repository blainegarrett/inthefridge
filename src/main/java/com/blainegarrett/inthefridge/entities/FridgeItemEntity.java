package com.blainegarrett.inthefridge.entities;

public class FridgeItemEntity {
  private String id;
  private FridgeEntity fridge;
  private ItemEntity item;

  public static FridgeItemEntity create(String id, FridgeEntity fridge, ItemEntity item) {
    return new FridgeItemEntity(id, fridge, item);
  }

  private FridgeItemEntity(String id, FridgeEntity fridge, ItemEntity item) {
    this.id = id;
    this.fridge = fridge;
    this.item = item;
  }

  public String getId() {
    return this.id;
  }
  public FridgeEntity getFridge() {
    return this.fridge;
  }
  public ItemEntity getItem() {
    return this.item;
  }
}
