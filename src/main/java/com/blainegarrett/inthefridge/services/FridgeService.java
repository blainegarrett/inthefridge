package com.blainegarrett.inthefridge.services;

import com.blainegarrett.inthefridge.entities.FridgeEntity;
import com.blainegarrett.inthefridge.entities.ItemEntity;
import com.blainegarrett.inthefridge.entities.FridgeItemEntity;
import com.blainegarrett.inthefridge.exceptions.ItemNotInFridgeException;
import com.blainegarrett.inthefridge.exceptions.TooMuchSodaException;
import com.blainegarrett.inthefridge.repositories.IFridgeRepository;

import java.util.List;
import java.util.Optional;

public class FridgeService {
  IFridgeRepository repo;
  private int MAX_ALLOWED_SODA_ITEMS = 12;

  public FridgeService(IFridgeRepository repo) {
    this.repo = repo;
  }

  /**
   * Get a list of all Fridges
   * @return A List of FridgeEntity Objects
   */
  public List<FridgeEntity> getFridges() {
    return this.repo.query();
  }

  /**
   * Get a Fridge By Id
   * @param id Id of Fridge
   * @return Fridge Optional
   */
  public Optional<FridgeEntity> getById(String id) {
    return this.repo.getById(id);
  }

  /**
   * Given an Item, attempt to add it to the fridge.
   * @param fridge Instance of FridgeEntity to add to to
   * @param item Instance of ItemEntity to add to Fridge
   * @throws TooMuchSodaException if there is too much soda in the fridge
   * @return Optional of FridgeItem if item was added
   */
  public Optional<FridgeItemEntity> addItemToFridge(FridgeEntity fridge, ItemEntity item) throws TooMuchSodaException {
    // Isolate Item Type
    String itemType = item.getType();
    if (itemType.equals("soda")) {

      // Get total items in fridge by type
      int total = repo.getCountByType(fridge.getId(), itemType);

      if (total >= MAX_ALLOWED_SODA_ITEMS) {
        throw new TooMuchSodaException("You have too many cans of soda in your fridge.");
      }
    }

    // Add item to repository
    Optional<FridgeItemEntity> result = repo.addItem(fridge.getId(), item.getId(), item.getType());
    return result;
  }

  /**
   * Remove an FridgeItem From a Fridge
   * @param fridge Instance of Fridge
   * @param fridgeItem Instance of Fridge Item
   * @throws ItemNotInFridgeException if item belongs to a different fridge
   * @return boolean if item was successfully removed
   */
  public boolean removeItemFromFridge(FridgeEntity fridge, FridgeItemEntity fridgeItem) throws ItemNotInFridgeException{
    if (!fridgeItem.getFridge().getId().equals(fridge.getId())) {
      throw new ItemNotInFridgeException("This item is in another fridge");
    }

    return repo.deleteFridgeItem(fridgeItem.getId());
  }

  /**
   * Get a List of Fridge Items from the Fridge
   * @param fridge Instance of a Fridge
   * @return A list of FridgeItemEntities
   */
  public List<FridgeItemEntity> getItemsFromFridge(FridgeEntity fridge) {
    return repo.getFridgeItems(fridge.getId());
  }

  /**
   * Get an Item from a Fridge
   * @param fridge Target Fridge
   * @param fridgeItemId Id of Target Item
   * @return Optional of FridgeItemEntity
   * @throws ItemNotInFridgeException if Item is in a different Fridge
   */
  public Optional<FridgeItemEntity> getItemFromFridge(FridgeEntity fridge, String fridgeItemId) throws ItemNotInFridgeException{
    Optional<FridgeItemEntity> fridgeItemOptional = repo.getFridgeItem(fridgeItemId);

    if (!fridgeItemOptional.isEmpty()) {
      // Resolve Optional
      FridgeItemEntity fridgeItem = fridgeItemOptional.get();

      if (!fridgeItem.getFridge().getId().equals(fridge.getId())) {
        throw new ItemNotInFridgeException("This item is in another fridge");
      }
    }

    return fridgeItemOptional;
  }
}
