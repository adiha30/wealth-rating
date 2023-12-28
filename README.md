# WealthRatingController

The `WealthRatingController` is a RESTful API controller that handles requests related to wealth rating. It is part of the Wealth Rating Application, a Java-based application that evaluates the wealth status of individuals.

## Endpoints

The controller exposes the following endpoints:

### POST /api/v1/wealth-rating/check

This endpoint evaluates the rich status of a person based on their financial information. It accepts a JSON body representing a `Person` object and returns a `RichState` object indicating the evaluated wealth status of the person.

### GET /api/v1/wealth-rating/rich/all

This endpoint retrieves a list of all individuals who have been evaluated as rich. It returns a list of `PersonEntity` objects representing the rich individuals.

### GET /api/v1/wealth-rating/rich/{id}

This endpoint retrieves information about a rich individual based on their ID. It accepts a path variable representing the ID of the rich individual and returns a `PersonEntity` object representing the rich individual.

## Error Handling

The controller includes error handling for various scenarios, such as invalid data, server errors, and not found errors. It logs these errors and returns appropriate HTTP status codes.

## Dependencies

The controller depends on the `WealthRatingService` for performing operations related to wealth rating. It also uses the `Logger` from the SLF4J library for logging.

## Usage

To use the controller, send HTTP requests to the above endpoints. Ensure that the requests are formatted correctly and include any necessary data.
