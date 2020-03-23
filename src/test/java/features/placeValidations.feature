Feature: Validating Place API

  Scenario Outline: Verify if Place is being successfully added using AddPlace API
    Given AddPlace Payload With "<name>", "<language>" and "<address>"
    When I call "AddPlaceAPI" with "POST" http request
    Then the API call is success with code 200
    And "status" in response body is "OK"
    And "scope" in response body is "APP"
    And verify placeID created maps to "<name>" using "getPlaceAPI"

    Examples:
    |name   | language | address |
    |A House| English  | UK      |
    |B House| American | USA     |