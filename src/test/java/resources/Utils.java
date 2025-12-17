package resources;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Properties;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

public class Utils  {
	PrintStream log;
	public static RequestSpecification reqSpec;
	public static RequestSpecification getReqSpec;
	public RequestSpecification requestSpecification() throws IOException
	{   
		if(reqSpec==null)
		{
		 if (log == null) {
	            // Append = true → DO NOT override logs
	            log = new PrintStream(new FileOutputStream("logging.txt")); 
	            }
		reqSpec = new RequestSpecBuilder()
										.addFilter(RequestLoggingFilter.logRequestTo(log))
										.addFilter(ResponseLoggingFilter.logResponseTo(log))
										.setBaseUri(getGlobalValue("baseUrl"))
										.addQueryParam("key", "qaclick123")
										.setContentType(ContentType.JSON).build();		
		}
		return reqSpec;
	}
	public ResponseSpecification responseSpecification()
	{
		ResponseSpecification resSpec = new ResponseSpecBuilder()
											.expectStatusCode(200)
											.expectContentType(ContentType.JSON).build();
		return resSpec;
	}
	public String getGlobalValue(String key) throws IOException
	{
		Properties prop= new Properties();
		FileInputStream fis =new FileInputStream("C:\\Users\\rushi\\eclipse-workspace\\Z_SeleniumTesting4_RestAssured\\src\\test\\java\\resources\\globalRest.properties");
		prop.load(fis);
		return prop.getProperty(key);
	}
	public String getJsonPath(Response resource, String key)
	{
		String resp= resource.asString();
		JsonPath js = new JsonPath(resp);
		String value= js.get(key).toString();
		return value;
	}
}
