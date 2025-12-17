package restAssured;

import java.util.List;

import org.testng.Assert;

import io.restassured.path.json.JsonPath;
import restFiles.PayLoad;

public class ComplexJsonParse {
	public static void main(String args[]) {
		JsonPath js = new JsonPath(PayLoad.CoursePrice());
		
//		 * 1. Print No of courses returned by API
		int size = js.getInt("courses.size()");
		System.out.println("No of courses returned by API: "+size);
		
//		 * 2. Print Purchase Amount
		int purchaseAmt = js.getInt("dashboard.purchaseAmount");
		System.out.println("Purchase Amount: "+purchaseAmt);
		
//		 * 3. Print Title of the first course
		String FTitle = js.getString("courses[0].title");
		System.out.println(FTitle);
		
//		 * 4. Print All course titles and their respective Prices
		int sumOfCources = 0;
		for(int i=0;i<size; i++)
		{
			int price= js.getInt("courses["+i+"].price");
			int count = js.getInt("courses["+i+"].copies");
			System.out.println("Title: "+ js.getString("courses["+i+"].title")+ ", Price: "+price);
			sumOfCources +=price*count;

		}
//		 * 5. Print no of copies sold b y RPA Course
		String cName ="RPA";
		for(int i =0; i<size;i++)
		{
			String courceName = js.get("courses["+i+"].title");
			if(courceName.equalsIgnoreCase(cName))
			{
				int copies= js.getInt("courses["+i+"].copies");
				System.out.println(cName+" copies: "+copies);
				break;
			}
		}
//		 System.out.println("No. of copies sold by RPA course : "+js.getInt("courses[2].copies"));
		 
//		 * 6. Verify if Sum of all Course prices matches with Purchase Amount
		 
		 System.out.println("Sum of all cources: " + sumOfCources);
		 Assert.assertEquals(sumOfCources, purchaseAmt);
		 
		 
		
	}

}
