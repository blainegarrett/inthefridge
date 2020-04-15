package com.blainegarrett.inthefridge.repositories;


import com.blainegarrett.inthefridge.entities.FridgeEntity;

import java.util.List;
import java.util.Optional;

public interface IFridgeRepository {
  public Optional<FridgeEntity> getById(String id);
  public List<FridgeEntity> query();
}