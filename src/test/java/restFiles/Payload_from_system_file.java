package restFiles;
import org.testng.annotations.Test;

import io.restassured.RestAssured;
import static io.restassured.RestAssured.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
public class Payload_from_system_file 
{

	@Test
	public void addMapPayloadWithfile() throws IOException
	{
	RestAssured.baseURI= "https://rahulshettyacademy.com";
	String response = 
			given().log().all()
					.queryParam("key", "qaclick123")
					.header("Content-Type", "application/json")
					.body(new String (Files.readAllBytes(Paths.get("C:\\Users\\rushi\\Documents\\addPlace.json"))))
			.when().post("maps/api/place/add/json")
			.then().extract().response().asString();
	

	}
}
