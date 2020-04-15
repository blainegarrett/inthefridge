package com.blainegarrett.inthefridge.services;

import com.blainegarrett.inthefridge.entities.ItemEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ItemService {

  public List<ItemEntity> getItems() {
    // TODO: This is stubbed out...

    List<ItemEntity> itemList = new ArrayList<>();

    // TODO: This data is stubbed out to establish tests until repository is in place
    itemList.add(ItemEntity.create("izzygrape", "soda"));
    itemList.add(ItemEntity.create("milk", "dairy"));

    return itemList;
  }

  public Optional<ItemEntity> getById(String id) {
    // TODO: Stubbed out until repo established
    if (id == "nails") {
      return Optional.empty();
    }

    var item = ItemEntity.create(id, "jive");
    return Optional.of(item);
  }
}
