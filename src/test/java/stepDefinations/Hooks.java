package stepDefinations;

import java.io.IOException;

import io.cucumber.java.Before;

public class Hooks {

	@Before("@DeletePlace")
	public void beforeScenario() throws IOException
	{
		
		placeValidationStepdef placesdf = new placeValidationStepdef();
		if(placeValidationStepdef.placeId==null)
		{
		placesdf.add_place_payload(100, "rush", "(+91) 1234567890 ", "10, dhule, maharastra ", "http://google.com", "german");
		placesdf.user_calls_with_http_request("AddPlaceAPI", "post");
		placesdf.verify_the_place_id_created_maps_to_using("rush", "GetPlaceAPI");
	}
	}
}
