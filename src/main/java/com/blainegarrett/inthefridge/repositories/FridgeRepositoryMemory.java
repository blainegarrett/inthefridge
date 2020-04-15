package com.blainegarrett.inthefridge.repositories;

import com.blainegarrett.inthefridge.entities.FridgeEntity;
import com.blainegarrett.inthefridge.entities.FridgeItemEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class FridgeRepositoryMemory implements IFridgeRepository  {
  @Override
  public List<FridgeEntity> query() {
    List<FridgeEntity> itemList = new ArrayList<>();
    // TODO: Flesh this out for integration tests
    return itemList;
  }

  @Override
  public Optional<FridgeEntity> getById(String id) {
    // TODO: Flesh this out for integration tests
    return Optional.empty();
  }

  @Override
  public int getCountByType(String fridgeId, String itemType) {
    return 612;
  }

  @Override
  public Optional<FridgeItemEntity> addItem(String fridgeId, String itemId, String itemType) {
    // TODO: Flesh this out for integration tests
    return Optional.empty();
  }

  @Override
  public boolean deleteFridgeItem(String fridgeItemId) {
    // TODO: Flesh this out for integration tests
    return false;
  }

  @Override
  public List<FridgeItemEntity> getFridgeItems(String fridgeId) {
    // TODO: Flesh this out for integration tests
    return null;
  }
}
