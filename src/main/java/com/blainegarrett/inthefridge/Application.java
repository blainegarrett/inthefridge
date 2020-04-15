
package com.blainegarrett.inthefridge;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class Application {

  public static void main(String[] args) {
    SpringApplication.run(Application.class, args);
  }

  @GetMapping("/")
  public String rootHandler() {
    return "What is in the frige? See https://github.com/blainegarrett/inthefridge to find out.";
  }

  @GetMapping("/rest/friges/{fridgeId}")
  public String getFridgeHandler( @PathVariable("fridgeId") String fridgeId) {
    return "TODO: Show fridge with ID: " + fridgeId;
  }
}
