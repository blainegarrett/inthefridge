# What's In the Fridge?
Simple HTTP REST Service built with Spring Boot on Java 11 to Manage the Contents of Your Fridge

## Running the Code
There is a Makefile that abstracts away the maven commands for convenience. 

#### Installing
The application leverages Maven. To install the application and its dependencies, simply run:

```make install```

#### Running Unit Tests
The [service layer](https://github.com/blainegarrett/inthefridge/tree/master/src/test/java/com/blainegarrett/inthefridge/services) is fully tested with unit tests that mock out the repository interactions. Coverage reporting will be added in [#9](https://github.com/blainegarrett/inthefridge/issues/9). 
There is a set of [Application layer tests](https://github.com/blainegarrett/inthefridge/blob/master/src/test/java/com/blainegarrett/inthefridge/ApplicationTest.java) the test interactions with the handlers. Currently, sans dependency injection, the Respsitory layer is not mocked out and thus the test handle empty database error states.

```make test ```

#### Running the Application Locally
Prior to [Implementing The Database](https://github.com/blainegarrett/inthefridge/issues/8), running the application locally demonstrates the endpoints (described below) but does not return any data. To launch the Spring Boot application:

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

Get a specific item by its id. Returns an `ItemEntity` or throws 404 Not Found
```$xslt
GET /rest/items/{itemId}
```

Get a list of available fridges. Returns `List<FridgeEntity>`
```$xslt
GET /rest/fridges
```
Get a specific fridge by its id. Returns `FridgeEntity` or throw 404 Not Found
```$xslt
GET /rest/fridges/{fridgeId}
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

### Response Objects 
An `ItemEntity` is an object that contains an id identifying the item and a type classifier. These items are predefied in the database and can be though of as a list of available products anyone can put into their fridge.
```$xslt
{"id":"pepsi","type":"soda"}
```

A `FridgeEntity` represents a fridge that can contain items.
```
{"id":"blaineFridge"},
```
```
{"id":"katieFridge"}]
```

A `FridgeItemEntity` represents an instance of a `ItemEntity` in a `FridgeEntity`. It has it's own `id` and contains subfields `fridge` and `item` with the respective `FridgeEntity` and `ItemEntity`

```
{
    "id":"myCoke",
    "fridge":{"id":"myFridge"},
    "item":{"id":"coke","type":"soda"}
}
```

## Next Steps
Due to time constraints, there are several remaining TODOs that have been identified with github issues.
- [Implement Dependency Injection](https://github.com/blainegarrett/inthefridge/issues/6)
- [Setup Tracing with Cloud Trace](https://github.com/blainegarrett/inthefridge/issues/7)
- [Wire Up Live Databas](https://github.com/blainegarrett/inthefridge/issues/8)
- [Implement Code Coverage](https://github.com/blainegarrett/inthefridge/issues/9)
- [Implement Basic Authentication/Authorization](https://github.com/blainegarrett/inthefridge/issues/10)

## Final Notes 
This was my first time standing up a full Spring Boot application from scratch. My experience with JAVA has always been more lower level. More of the allocated time was spent dealing with configuration and unsuccessfully wrestling with libraries like Jacoco than I would have liked.

I dedicated most of the actual development time to the core business logic. The emphasis on unit testing the service layer is reflective of this. Additionally, how I approached the design process can be seen in the [Pull Requests](https://github.com/blainegarrett/inthefridge/pulls?q=is%3Apr+is%3Aclosed) I created after each significant milestone. 

There are several artifacts in the code that resulted from running out of time that might seem odd. Specifically, the [firebase dependencies](https://github.com/blainegarrett/inthefridge/blob/master/pom.xml#L87-L92) and [stubbed Repository pattern](https://github.com/blainegarrett/inthefridge/tree/master/src/main/java/com/blainegarrett/inthefridge/repositories) feels like cruft in lieu of not having dependency injection in place yet. 
