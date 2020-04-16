package com.blainegarrett.inthefridge.repositories;


import com.blainegarrett.inthefridge.entities.FridgeEntity;
import com.blainegarrett.inthefridge.entities.FridgeItemEntity;

import java.util.List;
import java.util.Optional;

public interface IFridgeRepository {
  public Optional<FridgeEntity> getById(String id);
  public List<FridgeEntity> query();
  public int getCountByType(String fridgeId, String itemType);
  public Optional<FridgeItemEntity> addItem(String fridgeId, String itemId, String itemType);
  public boolean deleteFridgeItem(String fridgeItemId);
  public List<FridgeItemEntity> getFridgeItems(String fridgeId);
  public Optional<FridgeItemEntity> getFridgeItem(String fridgeItemId);
}