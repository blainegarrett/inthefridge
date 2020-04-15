package com.blainegarrett.inthefridge.repositories;

import com.blainegarrett.inthefridge.entities.ItemEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ItemRepositoryFirebase implements IItemRepository  {
  @Override
  public List<ItemEntity> query() {
    List<ItemEntity> itemList = new ArrayList<>();
    // TODO: Flesh this out for integration tests
    return itemList;
  }

  @Override
  public Optional<ItemEntity> getById(String id) {
    // TODO: Flesh this out for integration tests
    return Optional.empty();
  }
}
