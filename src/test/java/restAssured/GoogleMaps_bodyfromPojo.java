package restAssured;
import static io.restassured.RestAssured.*;

import java.util.ArrayList;
import java.util.List;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import rest_pojo.Pojo_googleMaps;
import rest_pojo.Pojo_googleMaps_location;

public class GoogleMaps_bodyfromPojo {
	public static void main(String[] args)
	{
		//pojo class included: Pojo_googleMaps, Pojo_googleMaps_location
		Pojo_googleMaps pg= new Pojo_googleMaps();
		pg.setAccuracy(50);
		pg.setName("Frontline house");
		pg.setPhone_number("(+91) 983 893 3937");
		pg.setAddress("29, side layout, cohen 09");
		pg.setWebsite("http://google.com");
		pg.setLanguage("French-IN");
		List<String> type= new ArrayList<String>();
		type.add("shoe park");
		type.add("shop");
		pg.setTypes(type);
		
		Pojo_googleMaps_location l=new  Pojo_googleMaps_location();
		l.setLat(-38.383494);
		l.setLng(33.427362);
		pg.setLocation(l);
		
		
		
		RequestSpecification req = new RequestSpecBuilder().setBaseUri("https://rahulshettyacademy.com")
				                                           .setContentType(ContentType.JSON).build();
				                                           
        ResponseSpecification resSpec= new ResponseSpecBuilder().expectStatusCode(200).expectContentType(ContentType.JSON).build();
        RequestSpecification res = given().spec(req).log().all().queryParam("key", "qaclick123")
			.body(pg);
        Response response = res.when().post("/maps/api/place/add/json").
        								then().assertThat().statusCode(200).extract().response();
		
		String responseString = response.asString();
		System.out.println(responseString);
	}
}
