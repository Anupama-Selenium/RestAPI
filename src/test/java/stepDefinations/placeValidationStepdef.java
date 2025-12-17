package stepDefinations;
import static io.restassured.RestAssured.given;
import java.io.IOException;
import org.testng.Assert;
import org.testng.asserts.SoftAssert;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import resources.APIResources;
import resources.TestDataBuild;
import resources.Utils;

public class placeValidationStepdef extends Utils{

	RequestSpecification request;
	ResponseSpecification resSpec;
	Response response;
	String getResponse;
	TestDataBuild data = new TestDataBuild();
	APIResources resourceAPI;
	static String placeId;

	@Given("Add Place Payload {int},{string},{string},{string},{string},{string}")
	public void add_place_payload(int accuracy, String name, String phone, String address, String website, String language) throws IOException {
		request = given().spec(requestSpecification()).log().all().body(data.addPlacePayload(accuracy,name,phone,address,website,language));
	}
	
	@When("user calls {string} with {string} http request")
	public void user_calls_with_http_request(String resource, String httpMethod) {
		APIResources resourceAPI= APIResources.valueOf(resource);
		if(httpMethod.equalsIgnoreCase("POST"))
		{
		response = request.when().post(resourceAPI.getResource());
		}
		else if(httpMethod.equalsIgnoreCase("GET"))
		{
		response = request.when().get(resourceAPI.getResource());
		}
	}
	
	@Then("The API call got success with status code {int}")
	public void The_API_call_got_success_with_status_code(int statusCode) {
		response.then().spec(responseSpecification())
	     .extract().response();
		Assert.assertEquals(response.getStatusCode(), statusCode);
	}
	
	@Then("{string} in response body is {string}")
	public void in_response_body_is(String key, String expectedValue) {
		String actualKey = getJsonPath(response, key);
		SoftAssert asserts= new SoftAssert();
		asserts.assertEquals(actualKey, expectedValue);
		asserts.assertAll();
	}
	
	@Then("verify the placeId created maps to {string} using {string}")
	public void verify_the_place_id_created_maps_to_using(String expectedname, String resource) throws IOException 
	{
		placeId = getJsonPath(response, "place_id");
		request = given().spec(requestSpecification()).queryParam("place_id", placeId);
		user_calls_with_http_request(resource, "get");						
		String actualname = getJsonPath(response, "name");
		Assert.assertEquals(actualname,expectedname);
	}
	
	@Given("deletePlace Payload")
	public void delete_place_payload() throws IOException {
		request =given().spec(requestSpecification()).body(data.deletePlacePayload(placeId));
	}

}
