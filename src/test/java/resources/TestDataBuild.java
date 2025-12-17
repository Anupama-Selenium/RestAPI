package resources;

import java.util.ArrayList;
import java.util.List;

import rest_pojo.Pojo_googleMaps;
import rest_pojo.Pojo_googleMaps_location;

public class TestDataBuild 
{
	public Pojo_googleMaps addPlacePayload(int accuracy, String name, String phone, String address, String website, String language)
	{
		Pojo_googleMaps pg = new Pojo_googleMaps();
		pg.setAccuracy(accuracy);
		pg.setName(name);
		pg.setPhone_number(phone);
		pg.setAddress(address);
		pg.setWebsite(website);
		pg.setLanguage(language);
		List<String> type = new ArrayList<String>();
		type.add("shoe park");
		type.add("shop");
		pg.setTypes(type);
		Pojo_googleMaps_location l = new Pojo_googleMaps_location();
		l.setLat(-38.383494);
		l.setLng(33.427362);
		pg.setLocation(l);
		return pg;
	}
	 public String deletePlacePayload(String placeId)
	 {
		 return "{\r\n    \"place_id\":\""+placeId+"\"\r\n}" ;
	 }

	
}
