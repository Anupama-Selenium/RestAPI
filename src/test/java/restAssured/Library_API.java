package restAssured;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;

import static io.restassured.RestAssured.*;
import restFiles.PayLoad;
import restFiles.ReUsableMethods;

public class Library_API {
	// practical problem
	// Dynamically build json payload with external data inputs
	// parameterize the API Tests with multiple data sets
	// how to send static json files(payload) directly into post method of rest
	// assured
	

	@Test(dataProvider="booksData")
	public void addBook(String isbn, String aisle) 
	{
		RestAssured.baseURI = "http://216.10.245.166";
		String response = given().log().all()
								 .header("Content-Type", "application/json")
								.body(PayLoad.addBook(isbn, aisle))
						 .when().post("Library/Addbook.php")
						 .then().log().all().assertThat().statusCode(200)
						 		.extract().response().asString();
		JsonPath js =ReUsableMethods.rawToJason(response);
		String id=js.get("ID");
		System.out.println("ID: "+id);

	}
	@DataProvider(name="booksData")
	public Object[][] getBooksData()
	{
		return new Object[][] { {"anu", "7899"},
								{"rus", "4198"},
								{"lag", "5525"} };
	}
	
}
