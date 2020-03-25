package stepDefinitions;

import io.cucumber.java.Before;

import java.io.IOException;

public class Hooks {

    @Before("@deletePlace")
    public void beforeScenario() throws IOException {
        PlaceValidationSteps m = new PlaceValidationSteps();

        if(PlaceValidationSteps.placeId ==null) {

            m.addplace_Payload_With_and("Shetty", "French", "Asia");
            m.i_call_with_http_request("AddPlaceAPI", "POST");
            m.verify_placeID_created_maps_to_using("Shetty", "getPlaceAPI");
        }



    }
}
