package com.blainegarrett.inthefridge.services;

import com.blainegarrett.inthefridge.entities.FridgeEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class FridgeService {

  public List<FridgeEntity> getFridges() {
    // TODO: This is stubbed out...

    List<FridgeEntity> itemList = new ArrayList<>();

    // TODO: This data is stubbed out to establish tests until repository is in place
    itemList.add(FridgeEntity.create("blaineFridge"));
    itemList.add(FridgeEntity.create("katieFridge"));

    return itemList;
  }

  public Optional<FridgeEntity> getById(String id) {
    // TODO: Stubbed out until repo established
    if (id == "zachFridge") {
      return Optional.empty();
    }

    var item = FridgeEntity.create(id);
    return Optional.of(item);
  }
}
