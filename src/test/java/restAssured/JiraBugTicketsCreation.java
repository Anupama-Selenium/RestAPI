package restAssured;

import static io.restassured.RestAssured.*;

import java.io.File;

import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import restFiles.PayLoad;
import restFiles.ReUsableMethods;

public class JiraBugTicketsCreation {
	static String id="";
	@Test(priority = 1)
	public void createBug()
	{
	RestAssured.baseURI = "https://anupamand7.atlassian.net";
	String response =
	given().log().all().header("Content-Type", "application/json\r\n")
			.header("Authorization","Basic YW51cGFtYW5kN0BnbWFpbC5jb206QVRBVFQzeEZmR0YwWks2U1R3aEdzZFJudTlaNEFaYUc5MnNmVUlxUVpkQzc2S2VpSUZyUTdVZkMwYnR3Q0w2eGpnWTlPQzcwQlJob1c4dHhHZzlwNmFqWnNJZmg4XzZfSTdEVV9UQ3FOSWVDcXFGY0tINUcyUkNXckx4Smt1dWFLUlVYQWFJbEk0dUpHZlNIdF9oLWYxYUtKeFFYam9XUjNCUjYtdHJRNDNGUy1FdGRmdU5nVXhrPUFGQzlCRkI0")
			.body(PayLoad.createJiraBug())
	.when().post("rest/api/2/issue")
	.then().log().all()
			.assertThat().statusCode(201)
			.extract().response().asString();
			
	JsonPath js= ReUsableMethods.rawToJason(response);
	id = js.get("id");
	System.out.println(id);
	}
	
	@Test(priority = 2)
	public void attachmentToBug()
	{
		
		given().log().all()
				.pathParam("key", id)
				.header("X-Atlassian-Token", "no-check")
				.header("Authorization", "Basic YW51cGFtYW5kN0BnbWFpbC5jb206QVRBVFQzeEZmR0YwWks2U1R3aEdzZFJudTlaNEFaYUc5MnNmVUlxUVpkQzc2S2VpSUZyUTdVZkMwYnR3Q0w2eGpnWTlPQzcwQlJob1c4dHhHZzlwNmFqWnNJZmg4XzZfSTdEVV9UQ3FOSWVDcXFGY0tINUcyUkNXckx4Smt1dWFLUlVYQWFJbEk0dUpHZlNIdF9oLWYxYUtKeFFYam9XUjNCUjYtdHJRNDNGUy1FdGRmdU5nVXhrPUFGQzlCRkI0")
				.multiPart("file", new File("C:\\Users\\rushi\\Desktop\\Screenshot.png"))
		.when().post("rest/api/2/issue/{key}/attachments")
		.then().log().all().assertThat().statusCode(200).extract().response();
	}
	@Test(priority = 3)
	public void getBug()
	{
		System.out.println("GET REQUEST***************************************************");
		given().pathParam("key", id)
				.header("Content-Type", "application/json\r\n")
				.header("X-Atlassian-Token", "no-check")
				.header("Authorization", "Basic YW51cGFtYW5kN0BnbWFpbC5jb206QVRBVFQzeEZmR0YwWks2U1R3aEdzZFJudTlaNEFaYUc5MnNmVUlxUVpkQzc2S2VpSUZyUTdVZkMwYnR3Q0w2eGpnWTlPQzcwQlJob1c4dHhHZzlwNmFqWnNJZmg4XzZfSTdEVV9UQ3FOSWVDcXFGY0tINUcyUkNXckx4Smt1dWFLUlVYQWFJbEk0dUpHZlNIdF9oLWYxYUtKeFFYam9XUjNCUjYtdHJRNDNGUy1FdGRmdU5nVXhrPUFGQzlCRkI0")
		.when().get("rest/api/2/issue/{key}")
		.then().log().all();
	}
}
