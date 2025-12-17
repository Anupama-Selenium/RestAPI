package restAssured;
import static io.restassured.RestAssured.*;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import io.restassured.path.json.JsonPath;
public class OAuth2{

	public static void main(String args[]) throws InterruptedException
	{
//Get Code
		String code;
		String accessToken;
//************************************** IMP Note ***********************************************************
//  Google has recently added some restriction on OAuth2.0, so automation won't work for these below steps *******************
		
//		WebDriver driver = new ChromeDriver();
//		driver.get("https://accounts.google.com/o/oauth2/v2/auth?scope=https://www.googleapis.com/auth/userinfo.email&auth_url=https://accounts.google.com/o/oauth2/v2/auth&client_id=692183103107-p0m7ent2hk7suguv4vq22hjcfhcr43pj.apps.googleusercontent.com&response_type=code&redirect_uri=https://rahulshettyacademy.com/getCourse.php");
//		driver.manage().window().maximize();
////		driver.findElement(By.xpath("//div[contains(text(), 'Use another account')]")).click();
//		driver.findElement(By.xpath("//input[@type= 'email']")).sendKeys(email);
//		driver.findElement(By.xpath("//span[contains(text(), 'Next')]")).click();
//		driver.findElement(By.xpath("//input[contains(@type, 'password')]")).sendKeys(password);
//		driver.findElement(By.xpath("//span[contains(text(), 'Next')]")).click();
//		Thread.sleep(4000);	
		
		String url = "https://rahulshettyacademy.com/getCourse.php?code=4%2F0ATX87lPN5o8SoG788znZavyL1cXIXdwUFbRuJoo5R1izFt6XKPZGwrTjsojSap7h1Huscw&scope=email+https%3A%2F%2Fwww.googleapis.com%2Fauth%2Fuserinfo.email+openid&authuser=4&prompt=consent";
		String urlComp= url.split("code=")[1];
		code = urlComp.split("&")[0];
		System.out.println(code);		
		
//Get Access token		
		
		String responseAccessToken = 
				given().urlEncodingEnabled(false)
				.queryParams("code", code)
				.queryParams("client_id", "692183103107-p0m7ent2hk7suguv4vq22hjcfhcr43pj.apps.googleusercontent.com")
				.queryParams("client_secret", "erZOWM9g3UtwNRj340YYaK_W")
				.queryParams("redirect_uri", "https://rahulshettyacademy.com/getCourse.php")
				.queryParams("grant_type", "authorization_code")
				.when().log().all()
				.post("https://www.googleapis.com/oauth2/v4/token").asString();
		JsonPath js1 = new JsonPath(responseAccessToken);
		accessToken = js1.getString("access_token");
		System.out.println("AccessToken" +accessToken);
		System.out.println(responseAccessToken);
		
		
		String response = given().queryParam("access_token", accessToken)
		.when().post("https://rahulshettyacademy.com/getCourse.php")
		.asString();
		System.out.println(response);
	}
//__________________________************_code not working _************____________________________________________________________
}
