package restAssured;
import static io.restassured.RestAssured.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.testng.Assert;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import rest_pojo.Pojo_class_For_getCourse;
import rest_pojo.Pojo_cource_WebAutomation;
import rest_pojo.Pojo_cource_api;
public class OAuth_ClientCredentials 
{
	public static void main(String args[])
	{
		String array[]= {"Selenium Webdriver Java", "Cypress", "Protractor"}; 
		RestAssured.baseURI="https://rahulshettyacademy.com";
		String response= 
		given().formParam("client_id", "692183103107-p0m7ent2hk7suguv4vq22hjcfhcr43pj.apps.googleusercontent.com")
				.formParam("client_secret", "erZOWM9g3UtwNRj340YYaK_W")
				.formParam("grant_type", "client_credentials")
				.formParam("scope", "trust")
				.log().all()
		.when().post("oauthapi/oauth2/resourceOwner/token")
				.asString();
	
		JsonPath js= new JsonPath(response);
		String access_token = js.getString("access_token");
		System.out.println(access_token);
		System.out.println("******************************");
		
		Pojo_class_For_getCourse gc=given().log().all().queryParam("access_token", access_token)
		.when().log().all().get("oauthapi/getCourseDetails")
		.as(Pojo_class_For_getCourse.class);
		
		System.out.println(gc.getLinkedIn());
		System.out.println(gc.getCourses().getMobile().get(0).getCourseTitle());
		
		List<Pojo_cource_api> ApiCources = gc.getCourses().getApi();
		for(int i=0; i<ApiCources.size();i++)
		{
			if(gc.getCourses().getApi().get(i).getCourseTitle().equalsIgnoreCase("SoapUI Webservices testing"))
			{
				System.out.println(ApiCources.get(i).getPrice());
			}
		}
		ArrayList<String> actual= new ArrayList<String>();
		List<Pojo_cource_WebAutomation> WebAutomationCources=gc.getCourses().getWebAutomation();
		for(int i=0;i<WebAutomationCources.size();i++)
		{
			actual.add(WebAutomationCources.get(i).getCourseTitle());
		}
		List<String> expected =Arrays.asList(array);
		Assert.assertEquals(actual, expected);
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
	}
}
