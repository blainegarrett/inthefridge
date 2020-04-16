
package com.blainegarrett.inthefridge;

import com.blainegarrett.inthefridge.entities.FridgeEntity;
import com.blainegarrett.inthefridge.entities.FridgeItemEntity;
import com.blainegarrett.inthefridge.entities.ItemEntity;
import com.blainegarrett.inthefridge.exceptions.ItemNotInFridgeException;
import com.blainegarrett.inthefridge.exceptions.TooMuchSodaException;
import com.blainegarrett.inthefridge.repositories.FridgeRepositoryFirebase;
import com.blainegarrett.inthefridge.repositories.IFridgeRepository;
import com.blainegarrett.inthefridge.repositories.IItemRepository;
import com.blainegarrett.inthefridge.repositories.ItemRepositoryFirebase;
import com.blainegarrett.inthefridge.services.FridgeService;
import com.blainegarrett.inthefridge.services.ItemService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;


@SpringBootApplication
@RestController
public class Application {

  public static void main(String[] args) {
    // Fireup the service
    SpringApplication.run(Application.class, args);
  }

  /**
   * Introductory Helper
   * @return
   */
  @GetMapping("/")
  @ResponseBody
  public String rootHandler() {
    return "What is in the fridge? See https://github.com/blainegarrett/inthefridge to find out.";

  }

  /**
   * Fetch a List of All Available Items to put in the Fridge
   * @return List of ItemEntities
   */
  @GetMapping("/rest/items")
  @ResponseBody
  public List<ItemEntity> getItemsHandler() {
    // TODO: Use DI to inject the Firebase Repository
    IItemRepository repo = new ItemRepositoryFirebase();
    ItemService itemService = new ItemService(repo);
    return itemService.getItems();
  }

  /**
   * Get a List of All Fridges
   * @return A List of FridgeEntity Objects
   */
  @GetMapping("/rest/fridges")
  @ResponseBody
  public List<FridgeEntity> getFridgesHandler() {
    // TODO: Use DI to inject the Firebase Repository
    IFridgeRepository repo = new FridgeRepositoryFirebase();
    FridgeService fridgeService = new FridgeService(repo);
    return fridgeService.getFridges();
  }

  /**
   * Get a FridgeEntity by Id
   * @param fridgeId The Id of the Fridge you want
   * @return A FridgeEntity object
   */
  @GetMapping("/rest/fridges/{fridgeId}")
  @ResponseBody
  public FridgeEntity getFridgeHandler( @PathVariable("fridgeId") String fridgeId) {
    // TODO: Use DI to inject the Firebase Repository
    IFridgeRepository repo = new FridgeRepositoryFirebase();
    FridgeService fridgeService = new FridgeService(repo);

    Optional<FridgeEntity> fridge = fridgeService.getById(fridgeId);
    if (fridge.isEmpty()) {
      throw new ResponseStatusException(
          HttpStatus.NOT_FOUND, "fridge not found"
      );
    }
    return fridge.get();
  }

  /**
   * Get the Contents of the Fridge
   * @param fridgeId Id of the desired Fridge
   * @return A list of FridgeItemEntity objects
   */
  @GetMapping("/rest/fridges/{fridgeId}/items")
  @ResponseBody
  public List<FridgeItemEntity> getFridgeContentsHandler(@PathVariable("fridgeId") String fridgeId) {
    // TODO: Use DI to inject the Firebase Repository
    IFridgeRepository repo = new FridgeRepositoryFirebase();
    FridgeService fridgeService = new FridgeService(repo);

    // Ensure the Fridge we're targeting exists
    Optional<FridgeEntity> fridge = fridgeService.getById(fridgeId);
    if (fridge.isEmpty()) {
      throw new ResponseStatusException(
          HttpStatus.NOT_FOUND, "fridge not found"
      );
    }
    // Get Contents
    return fridgeService.getItemsFromFridge(fridge.get());
  }

  /**
   * Add an Item to the Fridge
   * @param fridgeId ID of the Fridge to add Item to
   * @param itemId ID of the Item to add to the Fridge
   * @return The newly Added Item or Error if you attempt to add too much soda
   */
  @GetMapping("/rest/fridges/{fridgeId}/items/add/{itemId}") // TODO: USE POST VERB + BODY
  @ResponseBody
  public FridgeItemEntity addItemToFridgeHandler(@PathVariable("fridgeId") String fridgeId, @PathVariable("itemId") String itemId) {
    // TODO: Use DI to inject the Firebase Repository
    IFridgeRepository fridgeRepo = new FridgeRepositoryFirebase();
    FridgeService fridgeService = new FridgeService(fridgeRepo);
    IItemRepository itemRepo = new ItemRepositoryFirebase();
    ItemService itemService = new ItemService(itemRepo);

    // Ensure the Fridge we're targeting exists
    Optional<FridgeEntity> fridge = fridgeService.getById(fridgeId);
    if (fridge.isEmpty()) {
      throw new ResponseStatusException(
          HttpStatus.NOT_FOUND, "fridge not found"
      );
    }

    Optional<ItemEntity> item = itemService.getById(itemId);
    if (item.isEmpty()) {
      throw new ResponseStatusException(
          HttpStatus.NOT_FOUND, "item does not found"
      );
    }

    // Get Contents
    try {
      Optional<FridgeItemEntity> newFridgeItem = fridgeService.addItemToFridge(fridge.get(), item.get());
      // Optional returned empty...
      if (newFridgeItem.isEmpty()) {
        throw new ResponseStatusException(
            HttpStatus.INTERNAL_SERVER_ERROR, "unable to add item for unknown reason"
        );
      }
      else {
        // Success! Return the item
        return newFridgeItem.get();
      }
    } catch(TooMuchSodaException e) {
      // TOO MUCH SODA ERROR...
      throw new ResponseStatusException(
          HttpStatus.BAD_REQUEST, e.getMessage()
      );
    }
  }


  /**
   * Show an Item in the Fridge
   * @param fridgeId ID of the Fridge to add Item to
   * @param fridgeItemId ID of the Item in the Fridge
   * @return The FridgeItemEntity or Error if you attempt to add too much soda
   */
  @GetMapping("/rest/fridges/{fridgeId}/items/{fridgeItemId}")
  @ResponseBody
  public FridgeItemEntity getItemFromFridgeHandler(@PathVariable("fridgeId") String fridgeId, @PathVariable("fridgeItemId") String fridgeItemId) {
    // TODO: Use DI to inject the Firebase Repository
    IFridgeRepository fridgeRepo = new FridgeRepositoryFirebase();
    FridgeService fridgeService = new FridgeService(fridgeRepo);

    // Ensure the Fridge we're targeting exists
    Optional<FridgeEntity> fridge = fridgeService.getById(fridgeId);
    if (fridge.isEmpty()) {
      throw new ResponseStatusException(
          HttpStatus.NOT_FOUND, "fridge not found"
      );
    }

    // Get The Fridge Item From the Fridge
    Optional<FridgeItemEntity> fridgeItemOptional;

    try {
      fridgeItemOptional = fridgeService.getItemFromFridge(fridge.get(), fridgeItemId);
    } catch (ItemNotInFridgeException e) {
      throw new ResponseStatusException(
          HttpStatus.BAD_REQUEST, e.getMessage()
      );
    }

    return fridgeItemOptional.get();
  }


  /**
   * Delete an Item in the Fridge
   * @param fridgeId ID of the Fridge to remove Item from
   * @param fridgeItemId ID of the Item to remove from Fridge
   * @return Boolean if item was removed or Error if you item wasn't in the fridge
   */
  @GetMapping("/rest/fridges/{fridgeId}/items/{fridgeItemId}/delete") // TODO: Use DELETE VERB
  @ResponseBody
  public boolean deleteItemFromFridgeHandler(@PathVariable("fridgeId") String fridgeId, @PathVariable("fridgeItemId") String fridgeItemId) {
    // TODO: Use DI to inject the Firebase Repository
    IFridgeRepository fridgeRepo = new FridgeRepositoryFirebase();
    FridgeService fridgeService = new FridgeService(fridgeRepo);

    // Ensure the Fridge we're targeting exists
    Optional<FridgeEntity> fridge = fridgeService.getById(fridgeId);
    if (fridge.isEmpty()) {
      throw new ResponseStatusException(
          HttpStatus.NOT_FOUND, "fridge not found"
      );
    }

    // Get The Fridge Item From the Fridge
    Optional<FridgeItemEntity> fridgeItemOptional;

    try {
      fridgeItemOptional = fridgeService.getItemFromFridge(fridge.get(), fridgeItemId);
    } catch (ItemNotInFridgeException e) {
      throw new ResponseStatusException(
      HttpStatus.BAD_REQUEST, e.getMessage()
      );
    }

    // Delete the item
    try {
      return fridgeService.removeItemFromFridge(fridge.get(), fridgeItemOptional.get());
    } catch(ItemNotInFridgeException e) {
      throw new ResponseStatusException(
          HttpStatus.BAD_REQUEST, e.getMessage()
      );
    }
  }
}
