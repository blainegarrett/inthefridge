package com.blainegarrett.inthefridge.services;

import com.blainegarrett.inthefridge.entities.FridgeEntity;
import com.blainegarrett.inthefridge.repositories.IFridgeRepository;

import java.util.List;
import java.util.Optional;

public class FridgeService {
  IFridgeRepository repo;
  public FridgeService(IFridgeRepository repo) {
    this.repo = repo;
  }

  public List<FridgeEntity> getFridges() {
    return this.repo.query();
  }

  public Optional<FridgeEntity> getById(String id) {
    return this.repo.getById(id);
  }
}
