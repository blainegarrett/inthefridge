package com.blainegarrett.inthefridge.repositories;


import com.blainegarrett.inthefridge.entities.ItemEntity;

import java.util.List;
import java.util.Optional;

public interface IItemRepository {
  public Optional<ItemEntity> getById(String id);
  public List<ItemEntity> query();
}