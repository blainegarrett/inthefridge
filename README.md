# What's In the Fridge?
Simple HTTP Server Built on Spring Boot on Java 11 to Manage the Contents of Your Fridge

## Running the Code
There is a Makefile that abstracts away the maven commands for convenience. 

#### Installing
The application leverages Maven. To install the application and its dependencies, simply run:

```make install```

#### Running Unit Tests
The service layer is fully tested with unit tests that mock out the repository interactions. 
There is a set of Application layer tests the test interactions with the handlers. Currently, sans dependency injection, the Respsitory layer is not mocked out and thus the test handle empty database error states.

```make test ```

#### Running the Application Locally
Prior to [https://github.com/blainegarrett/inthefridge/issues/8](Implementing The Database), running the application locally demonstrates the endpoints (described below) but does not return any data. To launch the Spring Boot application:

```make dev ```




#### Deploying to Google App Engine 
The service was designed to be deployed to Google Cloud Platform's App Engine Java Standard Environment.
To deploy to your own appspot, follow [this guide to setup your project](https://cloud.google.com/appengine/docs/standard/java11/quickstart). Once you have a project ID, change [the project id argument](https://github.com/blainegarrett/inthefridge/blob/master/Makefile#L17) in the Makefile. Then run:

```make deploy```

## Endpoints 
The following endpoints are implemented. All endpoints return 200 status with described json payload on success or applicable error codes in json format with corresponding HTTP status code.


Api Root Handler - functions as a documentation endpoint.
```$xslt
GET / 
```

Get items available to put in fridges. Returns `List<ItemEntity>`
```$xslt
GET /rest/items
```

Get a specific item by its id. Returns a `ItemEntity` or throws 404 Not Found
```$xslt
GET /rest/items/<itemId>
```

Get a list of available fridges. Returns `List<FridgeEntity>`
```$xslt
GET /rest/fridges
```
Get a specific fridge by its id. Returns `FridgeEntity` or throw 404 Not Found
```$xslt
GET /rest/fridges/<fridgeId>
```

List contents of fridge. Returns `List<FridgeItemEntity>` or throw 404 Not Found if fridge does not exist.
```$xslt
GET /rest/fridges/{fridgeId}/items
```

Add an item to the fridge. Returns the newly created `FridgeItemEntity` or throw 404 Not Found if fridge does not exist. Additionally, if the `item_id` belongs to a `ItemEntity` with `type` "soda" and there are more than 12 items of type soda in the fridge already, the endpoint will throw a 405 BAD REQUEST status.
```$xslt
GET /rest/fridges/{fridgeId}/items/add/{itemId}
```

Get a specific item in the fridge. Returns a `FridgeItemEntity` or throw 404 Not Found if fridge does not exist or the item does not exist.
```$xslt
GET /rest/fridges/{fridgeId}/items/{itemId}
```

Delete a specific item in the fridge. Returns a `boolean` if the item was deleted or throws 404 Not Found if fridge does not exist or the item does not exist.

```$xslt
GET /rest/fridges/{fridgeId}/items/{fridgeItemId}/delete
```

### Response types 
An `ItemEntity` is an object that contains an id identifying the item and a type classifier. These items are predefied in the database and can be though of as a list of available products anyone can put into their fridge.
```$xslt
eg. {"id":"pepsi","type":"soda"}
eg. {"id":"milk","type":"dairy"}
```

A `FridgeEntity` represents a fridge that can contain items.
```
eg. {"id":"blaineFridge"},{"id":"katieFridge"}]
```

A `FridgeItemEntity` represents an instance of a `ItemEntity` in a `FridgeEntity`. It has it's own `id` and contains subfields `fridge` and `item` with the the respective `FridgeEntity` and `ItemEntity`

```
{
    "id":"myCoke",
    "fridge":{"id":"myFridge"},
    "item":{"id":"coke","type":"soda"}
}
```

## Next Steps
Due to time constraints, there are several remaining TODOs
- [Implement Dependency Injection](https://github.com/blainegarrett/inthefridge/issues/6)
- [Setup Tracing with Cloud Trace](https://github.com/blainegarrett/inthefridge/issues/7)
- [Wire Up Live Databas](https://github.com/blainegarrett/inthefridge/issues/8)
- [Implement Code Coverage](https://github.com/blainegarrett/inthefridge/issues/9)
- [Implement Basic Authentication/Authorization](https://github.com/blainegarrett/inthefridge/issues/10)

