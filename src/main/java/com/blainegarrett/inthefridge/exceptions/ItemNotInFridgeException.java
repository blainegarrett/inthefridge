package com.blainegarrett.inthefridge.exceptions;

public class ItemNotInFridgeException extends Exception{
  public ItemNotInFridgeException(String msg) {
    super(msg);
  }
}
