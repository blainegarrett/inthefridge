package com.blainegarrett.inthefridge.services;

import com.blainegarrett.inthefridge.entities.ItemEntity;
import com.blainegarrett.inthefridge.repositories.IItemRepository;

import java.util.List;
import java.util.Optional;

public class ItemService {
  IItemRepository repo;

  public ItemService(IItemRepository repo) {
    this.repo = repo;
  }

  public List<ItemEntity> getItems() {
    return this.repo.query();
  }

  public Optional<ItemEntity> getById(String id) {
    return this.repo.getById(id);
  }
}
