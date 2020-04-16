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

  /**
   * Get a List of All Available Items
   * @return A List of ItemEntities
   */
  public List<ItemEntity> getItems() {
    return this.repo.query();
  }

  /**
   * Get an Item by Id
   * @return A ItemEntity Optional
   */
  public Optional<ItemEntity> getById(String id) {
    return this.repo.getById(id);
  }
}
