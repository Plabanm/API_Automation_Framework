package stepDefinitions;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import resources.APIResources;
import resources.TestDataBuild;
import resources.Utils;

import java.io.IOException;

import static org.junit.Assert.*;
import static io.restassured.RestAssured.given;


public class PlaceValidationSteps extends Utils {
    RequestSpecification reqSpec;
    ResponseSpecification resSpec;
    Response response;
    TestDataBuild data = new TestDataBuild();

    @Given("AddPlace Payload With {string}, {string} and {string}")
    public void addplace_Payload_With_and(String name, String language, String address) throws IOException {
        reqSpec = given().spec(RequestSpecification())
                .body(data.addPlacePayload(name, language, address));
    }

    @When("I call {string} with {string} http request")
    public void i_call_with_http_request(String resource, String method) {
        APIResources endPoint = APIResources.valueOf(resource);


        resSpec =new ResponseSpecBuilder().expectStatusCode(200).expectContentType(ContentType.JSON).build();

        if(method.equalsIgnoreCase("POST"))
            response = reqSpec.when().post(endPoint.getResource());
        else if(method.equalsIgnoreCase("GET"))
            response = reqSpec.when().get(endPoint.getResource());
    }
    @Then("the API call is success with code {int}")
    public void the_API_call_is_success_with_code(Integer statusCode) {
        assertEquals(200, response.getStatusCode());
    }
    @Then("{string} in response body is {string}")
    public void in_response_body_is(String key, String expectedValue) {
        assertEquals(expectedValue, getJsonPath(response,key));
    }
    @Then("verify placeID created maps to {string} using {string}")
    public void verify_placeID_created_maps_to_using(String expectedName, String resource) throws IOException {
        String placeId = getJsonPath(response,"place_id");
        reqSpec = given().spec(RequestSpecification()).queryParam("place_id",placeId);
        i_call_with_http_request(resource, "GET");
        String actualName = getJsonPath(response,"name");
        assertEquals(actualName,expectedName );

    }
}
