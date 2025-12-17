package restAssured;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import restFiles.PayLoad;
import restFiles.ReUsableMethods;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

import org.testng.Assert;
public class GoogleMaps_API {
//	Validate if add place API is working as expected or not
//	Given, When, Then
//	Given - all input details
//	When - submit API
//	Then - validate the response
	public static void main(String args[])
	{
		RestAssured.baseURI = "https://rahulshettyacademy.com";
//POST:  Validate if add place API is working as expected
		
/*		ValidatableResponse varification = given().log().all()
		 	   										.queryParam("key", "qaclick123")
		 	   										.header("Content-Type", "application/json")
		 	   										.body(PayLoad.AddPlace())
		 	   							   .when().post("maps/api/place/add/json")
		 	   							   .then().assertThat().statusCode(200)
		 	   										.body("scope", equalTo("APP"))
		 	   										.header("server", "Apache/2.4.52 (Ubuntu)");
*/	       
		String postPlaceResponse = given().log().all()
			 	   							.queryParam("key", "qaclick123")
			 	   							.header("Content-Type", "application/json")
			 	   							.body(PayLoad.AddPlace())
			 	   				   .when().post("maps/api/place/add/json")
								   .then().extract()
											.response()
											.asString();
		JsonPath js = ReUsableMethods.rawToJason(postPlaceResponse);
		String placeId= js.getString("place_id");
		System.out.println(placeId);
		
//PUT:   Add Place -> update place with new address -> get place to validate if new address is present in response
		 String newAddress = "Japan";
		        given()
		 		.queryParam("qaclick123", "qaclick123")
		 		.header("Content-Type", "application/json")
		 		.body("{\r\n"
		 				+ "\"place_id\":\""+placeId+"\",\r\n"
		 				+ "\"address\":\""+newAddress+"\",\r\n"
		 				+ "\"key\":\"qaclick123\"\r\n"
		 				+ "}\r\n"
		 				+ "")
		        .when()
		 		.put("maps/api/place/update/json")
		        .then()
		        .log().all()
		 		.assertThat().statusCode(200)
		 		.body("msg", equalTo("Address successfully updated"));
		 
//GET:   Get place
		 String getPlaceResponse =  given()
		 									.queryParam("key", "qaclick123")
		 									.queryParam("place_id", placeId) 
		 						   .when()
		 						   		 	.get("maps/api/place/get/json")
		 						   .then()
		 						   			.extract().response().asString();
		JsonPath js1= ReUsableMethods.rawToJason(getPlaceResponse);
		String actualAddress = js1.getString("address");
		Assert.assertEquals(actualAddress, newAddress);
	}
}
